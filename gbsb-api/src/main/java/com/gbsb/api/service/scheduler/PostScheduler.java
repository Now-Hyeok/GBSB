package com.gbsb.api.service.scheduler;

import com.gbsb.api.crawler.PostCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ServiceLoader;


@Slf4j
@Component
@RequiredArgsConstructor
public class PostScheduler {

    private final List<PostCrawler> postCrawlers;

    @Scheduled(cron = "0 0/10 * * * *") // 1
    public void postCrawlScheduler() {
        for (PostCrawler postCrawler : postCrawlers) {
            try {
                log.info("Start crawling: {}", postCrawler.getCompanyName().getName());
                postCrawler.crawlPosts();
            } catch (Exception e) {
                log.error(
                        "Post crawling failed. company={}",
                        postCrawler.getCompanyName().getName(),
                        e
                );
            }
        }
    }
}
