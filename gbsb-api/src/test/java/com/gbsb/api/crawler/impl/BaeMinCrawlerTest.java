package com.gbsb.api.crawler.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


class BaeMinCrawlerTest {
    @Test
    public void crawlerTest() throws Exception {

        int page = 1;

        while (true) {
            String url = (page == 1)
                    ? "https://techblog.woowahan.com/"
                    : "https://techblog.woowahan.com/?paged=" + page;

            System.out.println("크롤링 페이지: " + page);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .timeout(10_000)
                    .get();

            Elements posts = doc.select("div.post-item.firstpaint");

            if (posts.isEmpty()) {
                System.out.println("크롤링 종료");
                break;
            }

            for (Element post : posts) {
                Element linkEl = post.selectFirst("a");
                Element titleEl = post.selectFirst("h2.post-title");
                Element dateEl = post.selectFirst("time.post-author-date");
                Element authorEl = post.selectFirst("span.post-author-name");
                Element excerptEl = post.selectFirst("p.post-excerpt");

                String link = linkEl.attr("href");
                String title = titleEl.text();
                String date = dateEl.text();
                String author = authorEl.text();
                String excerpt = excerptEl.text();

                System.out.println("제목: " + title);
                System.out.println("날짜: " + date);
                System.out.println("작성자: " + author);
                System.out.println("링크: " + link);
                System.out.println("요약: " + excerpt);
                System.out.println("------------------------------------------------");
            }

            page++;
            Thread.sleep(500);
        }
    }

    static class test {
        public Double asd;
        public List<Integer> intList;

    }

    @Test
    void asd() {
        Integer asd;
    }



}