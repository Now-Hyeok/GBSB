package com.gbsb.api.crawler.impl;

import com.gbsb.api.crawler.PostCrawler;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoCrawler implements PostCrawler {

    private final CompanyRepository companyRepository;

    @Override
    public List<PostDto> crawlPosts() {
        Company company = companyRepository.findCompanyByName(CompanyName.KAKAO.getName())
                .orElseThrow(() -> new CrawlException("Kakao company not found"));

        List<PostDto> posts = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(company.getBlogUrl())
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15_000)
                    .get();

            // 카카오 테크 블로그의 포스트 목록 셀렉터
            Elements articles = doc.select("article.post-item, div.post-item, .elementor-post");

            log.info("Found {} articles from Kakao Tech Blog", articles.size());

            for (Element article : articles) {
                try {
                    PostDto post = parseArticle(article, company);
                    if (post != null) {
                        posts.add(post);
                    }
                } catch (Exception e) {
                    log.warn("Failed to parse article: {}", e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new CrawlException("Failed to crawl Kakao Tech Blog: " + e.getMessage(), e);
        }

        log.info("Successfully crawled {} posts from Kakao Tech Blog", posts.size());
        return posts;
    }

    private PostDto parseArticle(Element article, Company company) {
        PostDto post = new PostDto();

        // 제목과 URL 추출
        Element titleLink = article.selectFirst("a[href], h2 a, h3 a, .post-title a");
        if (titleLink == null) {
            return null;
        }

        String title = titleLink.text().trim();
        String url = titleLink.absUrl("href");

        if (title.isEmpty() || url.isEmpty()) {
            return null;
        }

        post.setTitle(title);
        post.setUrl(url);
        post.setCompanyId(company.getId());

        // 요약 추출
        Element summaryElement = article.selectFirst(".post-excerpt, .excerpt, p");
        if (summaryElement != null) {
            String summary = summaryElement.text().trim();
            if (!summary.isEmpty() && summary.length() <= 500) {
                post.setSummary(summary);
            }
        }

        // 썸네일 이미지 추출
        Element thumbnail = article.selectFirst("img");
        if (thumbnail != null) {
            String thumbnailUrl = thumbnail.absUrl("src");
            if (!thumbnailUrl.isEmpty()) {
                post.setThumbnailUrl(thumbnailUrl);
            }
        }

        // 날짜 추출
        Element dateElement = article.selectFirst("time, .post-date, .date");
        if (dateElement != null) {
            String dateStr = dateElement.attr("datetime");
            if (dateStr.isEmpty()) {
                dateStr = dateElement.text();
            }
            try {
                LocalDateTime publishedAt = parseDate(dateStr);
                post.setPublishedAt(publishedAt);
            } catch (Exception e) {
                log.debug("Failed to parse date: {}", dateStr);
                post.setPublishedAt(LocalDateTime.now());
            }
        } else {
            post.setPublishedAt(LocalDateTime.now());
        }

        // 태그 추출
        Elements tagElements = article.select(".post-tags a, .tags a, .tag");
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

    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return LocalDateTime.now();
        }

        // ISO 8601 형식 시도
        if (dateStr.contains("T")) {
            try {
                return LocalDateTime.parse(dateStr.substring(0, 19));
            } catch (Exception e) {
                // 계속 진행
            }
        }

        // 다양한 날짜 형식 시도
        String[] patterns = {
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd",
                "yyyy.MM.dd",
                "yyyy/MM/dd"
        };

        for (String pattern : patterns) {
            try {
                return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
            } catch (Exception e) {
                // 다음 패턴 시도
            }
        }

        return LocalDateTime.now();
    }

    @Override
    public CompanyName getCompanyName() {
        return CompanyName.KAKAO;
    }
}
