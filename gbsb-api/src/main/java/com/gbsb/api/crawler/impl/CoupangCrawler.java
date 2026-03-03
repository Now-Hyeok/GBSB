package com.gbsb.api.crawler.impl;

import com.gbsb.api.crawler.PostCrawler;
import com.gbsb.api.dto.PostDto;
import com.gbsb.api.entity.Company;
import com.gbsb.api.entity.enums.CompanyName;
import com.gbsb.api.exception.CrawlException;
import com.gbsb.api.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CoupangCrawler extends MediumRssCrawler implements PostCrawler {

    private static final String RSS_FEED_URL = "https://medium.com/feed/coupang-engineering";

    private final CompanyRepository companyRepository;

    @Override
    public List<PostDto> crawlPosts() {
        Company company = companyRepository.findCompanyByName(CompanyName.COUPANG.getName())
                .orElseThrow(() -> new CrawlException("Coupang company not found in DB"));
        return crawlFromMediumRss(RSS_FEED_URL, company);
    }

    @Override
    public CompanyName getCompanyName() {
        return CompanyName.COUPANG;
    }
}
