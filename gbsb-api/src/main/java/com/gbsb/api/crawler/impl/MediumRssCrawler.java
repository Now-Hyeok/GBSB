package com.gbsb.api.crawler.impl;

import com.gbsb.api.crawler.util.DateParseUtil;
import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.TagDto;
import com.gbsb.api.entity.Company;
import com.gbsb.api.exception.CrawlException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for crawling Medium-hosted tech blogs via RSS feed.
 * Medium RSS feeds are available at https://medium.com/feed/{publication}
 */
@Slf4j
public abstract class MediumRssCrawler {

    protected List<PostDto> crawlFromMediumRss(String feedUrl, Company company) {
        List<PostDto> posts = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(feedUrl)
                    .userAgent("Mozilla/5.0 (compatible; GBSBBot/1.0)")
                    .timeout(15_000)
                    .parser(Parser.xmlParser())
                    .get();

            Elements items = doc.select("item");

            log.info("Found {} items from RSS feed: {}", items.size(), feedUrl);

            for (Element item : items) {
                try {
                    PostDto post = parseRssItem(item, company);
                    if (post != null) {
                        posts.add(post);
                    }
                } catch (Exception e) {
                    log.warn("Failed to parse RSS item: {}", e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new CrawlException("Failed to crawl RSS feed " + feedUrl + ": " + e.getMessage(), e);
        }

        log.info("Successfully crawled {} posts from {}", posts.size(), feedUrl);
        return posts;
    }

    private PostDto parseRssItem(Element item, Company company) {
        PostDto post = new PostDto();

        String title = getElementText(item, "title");
        String link = getElementText(item, "link");

        if (title.isEmpty() || link.isEmpty()) {
            return null;
        }

        post.setTitle(title);
        post.setUrl(link);
        post.setCompanyId(company.getId());

        // Medium RSS <content:encoded> contains HTML content — extract summary from it
        Element contentEncoded = item.selectFirst("content\\:encoded, encoded");
        if (contentEncoded != null) {
            String htmlContent = contentEncoded.text();
            // Parse HTML content to extract plain text summary
            Document contentDoc = Jsoup.parse(htmlContent);
            String plainText = contentDoc.text();
            if (plainText.length() > 300) {
                post.setSummary(plainText.substring(0, 297) + "...");
            } else if (!plainText.isEmpty()) {
                post.setSummary(plainText);
            }

            // Extract first image as thumbnail
            Element firstImg = contentDoc.selectFirst("img");
            if (firstImg != null) {
                String imgSrc = firstImg.attr("src");
                if (!imgSrc.isEmpty()) {
                    post.setThumbnailUrl(imgSrc);
                }
            }
        }

        // Fallback: description field
        if (post.getSummary() == null) {
            String description = getElementText(item, "description");
            if (!description.isEmpty()) {
                Document descDoc = Jsoup.parse(description);
                String text = descDoc.text();
                if (text.length() > 300) {
                    post.setSummary(text.substring(0, 297) + "...");
                } else {
                    post.setSummary(text);
                }
            }
        }

        // Published date
        String pubDate = getElementText(item, "pubDate");
        if (!pubDate.isEmpty()) {
            post.setPublishedAt(DateParseUtil.parse(pubDate));
        } else {
            post.setPublishedAt(LocalDateTime.now());
        }

        // Medium uses <category> tags for post tags
        Elements categories = item.select("category");
        if (!categories.isEmpty()) {
            List<TagDto> tags = categories.stream()
                    .map(cat -> {
                        TagDto tagDto = new TagDto();
                        tagDto.setName(cat.text().trim());
                        return tagDto;
                    })
                    .filter(tag -> !tag.getName().isEmpty())
                    .collect(Collectors.toList());
            post.setTags(tags);
        }

        return post;
    }

    private String getElementText(Element parent, String tagName) {
        Element element = parent.selectFirst(tagName);
        return element != null ? element.text().trim() : "";
    }
}
