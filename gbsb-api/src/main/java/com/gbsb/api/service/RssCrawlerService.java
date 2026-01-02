package com.gbsb.api.service;

import com.gbsb.api.domain.Post;

@Service
public class RssCrawlerService {

    public List<Post> crawlRss(String rssUrl, Company company) {
        // Rome 라이브러리 사용
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new URL(rssUrl)));

        return feed.getEntries().stream()
                .map(entry -> convertToPost(entry, company))
                .collect(Collectors.toList());
    }

    private Post convertToPost(SyndEntry entry, Company company) {
        Post post = new Post();
        post.setTitle(entry.getTitle());
        post.setSummary(entry.getDescription().getValue());
        post.setUrl(entry.getLink());
        post.setPublishedAt(convertToLocalDateTime(entry.getPublishedDate()));
        post.setCompany(company);
        // 태그, 카테고리 추출 로직...
        return post;
    }
}