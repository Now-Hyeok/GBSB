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
public class TossCrawler implements PostCrawler {

    private final CompanyRepository companyRepository;

    @Override
    public List<PostDto> crawlPosts() {
        Company company = companyRepository.findCompanyByName(CompanyName.TOSS.getName())
                .orElseThrow(() -> new CrawlException("Toss company not found"));

        List<PostDto> posts = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(company.getBlogUrl())
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15_000)
                    .get();

            // Toss 테크 블로그의 포스트 목록 셀렉터
            // Toss 블로그는 React 기반 SPA일 가능성이 높으므로 다양한 셀렉터 시도
            Elements articles = doc.select("article, .article-item, .post-item, .card");

            log.info("Found {} articles from Toss Tech Blog", articles.size());

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

            // 대체 방법: 모든 링크에서 블로그 포스트로 보이는 것들 추출
            if (posts.isEmpty()) {
                Elements links = doc.select("a[href*='/article'], a[href*='/tech'], a[href*='/post']");
                log.info("Trying alternative method, found {} potential links", links.size());

                for (Element link : links) {
                    try {
                        PostDto post = parseLink(link, company);
                        if (post != null && !isDuplicate(posts, post)) {
                            posts.add(post);
                        }
                    } catch (Exception e) {
                        log.debug("Failed to parse link: {}", e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            throw new CrawlException("Failed to crawl Toss Tech Blog: " + e.getMessage(), e);
        }

        log.info("Successfully crawled {} posts from Toss Tech Blog", posts.size());
        return posts;
    }

    private PostDto parseArticle(Element article, Company company) {
        PostDto post = new PostDto();

        // 제목과 URL 추출
        Element titleLink = article.selectFirst("a[href]");
        if (titleLink == null) {
            return null;
        }

        // 제목 추출 - 다양한 위치 시도
        Element titleElement = article.selectFirst("h1, h2, h3, h4, .title, .headline");
        String title = titleElement != null ? titleElement.text().trim() : titleLink.text().trim();
        String url = titleLink.absUrl("href");

        if (title.isEmpty() || url.isEmpty() || !url.startsWith("http")) {
            return null;
        }

        post.setTitle(title);
        post.setUrl(url);
        post.setCompanyId(company.getId());

        // 요약 추출
        Element summaryElement = article.selectFirst("p, .description, .excerpt, .summary");
        if (summaryElement != null) {
            String summary = summaryElement.text().trim();
            if (!summary.isEmpty() && summary.length() <= 500) {
                post.setSummary(summary);
            } else if (!summary.isEmpty()) {
                post.setSummary(summary.substring(0, 500));
            }
        }

        // 썸네일 이미지 추출
        Element thumbnail = article.selectFirst("img");
        if (thumbnail != null) {
            String thumbnailUrl = thumbnail.absUrl("src");
            if (!thumbnailUrl.isEmpty() && thumbnailUrl.startsWith("http")) {
                post.setThumbnailUrl(thumbnailUrl);
            }
        }

        // 날짜 추출
        Element dateElement = article.selectFirst("time, .date, .published-at");
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
        Elements tagElements = article.select(".tag, .category, .label");
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

    private PostDto parseLink(Element link, Company company) {
        String url = link.absUrl("href");
        String title = link.text().trim();

        if (title.isEmpty() || url.isEmpty() || !url.startsWith("http")) {
            return null;
        }

        // 너무 짧은 제목이나 네비게이션 링크 제외
        if (title.length() < 5 || title.equalsIgnoreCase("more") || title.equalsIgnoreCase("read more")) {
            return null;
        }

        PostDto post = new PostDto();
        post.setTitle(title);
        post.setUrl(url);
        post.setCompanyId(company.getId());
        post.setPublishedAt(LocalDateTime.now());

        return post;
    }

    private boolean isDuplicate(List<PostDto> posts, PostDto newPost) {
        return posts.stream().anyMatch(p -> p.getUrl().equals(newPost.getUrl()));
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
                "yyyy/MM/dd",
                "MM/dd/yyyy"
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
        return CompanyName.TOSS;
    }
}
