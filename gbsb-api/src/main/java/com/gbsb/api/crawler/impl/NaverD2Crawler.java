package com.gbsb.api.crawler.impl;

import com.gbsb.api.crawler.PostCrawler;
import com.gbsb.api.crawler.util.DateParseUtil;
import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.TagDto;
import com.gbsb.api.entity.Company;
import com.gbsb.api.entity.enums.CompanyName;
import com.gbsb.api.exception.CrawlException;
import com.gbsb.api.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverD2Crawler implements PostCrawler {

    private final CompanyRepository companyRepository;

    @Override
    public List<PostDto> crawlPosts() {
        Company company = companyRepository.findCompanyByName(CompanyName.NAVER.getName())
                .orElseThrow(() -> new CrawlException("Naver company not found in DB"));

        List<PostDto> posts = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("https://d2.naver.com/helloworld")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15_000)
                    .get();

            Elements articles = doc.select(".post_article, article, .con_list li");

            log.info("Found {} articles from Naver D2", articles.size());

            for (Element article : articles) {
                try {
                    PostDto post = parseArticle(article, company);
                    if (post != null) {
                        posts.add(post);
                    }
                } catch (Exception e) {
                    log.warn("Failed to parse Naver D2 article: {}", e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new CrawlException("Failed to crawl Naver D2: " + e.getMessage(), e);
        }

        log.info("Successfully crawled {} posts from Naver D2", posts.size());
        return posts;
    }

    private PostDto parseArticle(Element article, Company company) {
        PostDto post = new PostDto();

        Element titleLink = article.selectFirst("a[href], h2 a, h3 a");
        if (titleLink == null) {
            return null;
        }

        String title = titleLink.text().trim();
        String url = titleLink.absUrl("href");

        if (title.isEmpty() || url.isEmpty()) {
            return null;
        }

        // Naver D2 uses relative paths like /helloworld/1234567
        if (!url.startsWith("http")) {
            url = "https://d2.naver.com" + titleLink.attr("href");
        }

        post.setTitle(title);
        post.setUrl(url);
        post.setCompanyId(company.getId());

        Element summaryElement = article.selectFirst(".post_txt, .con_desc, p");
        if (summaryElement != null) {
            String summary = summaryElement.text().trim();
            if (!summary.isEmpty() && summary.length() <= 500) {
                post.setSummary(summary);
            }
        }

        Element thumbnail = article.selectFirst("img");
        if (thumbnail != null) {
            String thumbnailUrl = thumbnail.absUrl("src");
            if (!thumbnailUrl.isEmpty()) {
                post.setThumbnailUrl(thumbnailUrl);
            }
        }

        Element dateElement = article.selectFirst("time, .date, .post_date, span.date");
        if (dateElement != null) {
            String dateStr = dateElement.attr("datetime");
            if (dateStr.isEmpty()) {
                dateStr = dateElement.text();
            }
            post.setPublishedAt(DateParseUtil.parse(dateStr));
        } else {
            post.setPublishedAt(LocalDateTime.now());
        }

        Elements tagElements = article.select(".tag a, .tags a");
        if (!tagElements.isEmpty()) {
            List<TagDto> tags = tagElements.stream()
                    .map(tag -> {
                        TagDto tagDto = new TagDto();
                        tagDto.setName(tag.text().trim().replace("#", ""));
                        return tagDto;
                    })
                    .filter(tag -> !tag.getName().isEmpty())
                    .collect(Collectors.toList());
            post.setTags(tags);
        }

        return post;
    }

    @Override
    public CompanyName getCompanyName() {
        return CompanyName.NAVER;
    }
}
