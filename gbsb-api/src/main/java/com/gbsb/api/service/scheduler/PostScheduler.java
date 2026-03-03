package com.gbsb.api.service.scheduler;

import com.gbsb.api.crawler.PostCrawler;
import com.gbsb.api.dto.PostDto;
import com.gbsb.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class PostScheduler {

    private final List<PostCrawler> postCrawlers;
    private final PostService postService;

    /**
     * 매일 오전 6시, 오후 6시에 크롤링 실행.
     * 각 크롤러는 독립적으로 실행되며, 하나가 실패해도 나머지는 계속 진행.
     */
    @Scheduled(cron = "0 0 6,18 * * *")
    public void postCrawlScheduler() {
        log.info("=== Starting scheduled crawl for all companies ===");
        int totalCrawled = 0;

        for (PostCrawler postCrawler : postCrawlers) {
            try {
                log.info("Start crawling: {}", postCrawler.getCompanyName().getName());
                List<PostDto> posts = postCrawler.crawlPosts();

                if (!posts.isEmpty()) {
                    postService.insertAllPosts(posts);
                    totalCrawled += posts.size();
                    log.info("Crawled and saved {} posts from {}",
                            posts.size(), postCrawler.getCompanyName().getName());
                } else {
                    log.info("No new posts from {}", postCrawler.getCompanyName().getName());
                }
            } catch (Exception e) {
                log.error("Post crawling failed. company={}",
                        postCrawler.getCompanyName().getName(), e);
            }
        }

        log.info("=== Crawl complete. Total posts crawled: {} ===", totalCrawled);
    }
}
