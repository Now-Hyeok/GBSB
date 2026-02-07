package com.gbsb.api.crawler.impl;

import com.gbsb.api.crawler.PostCrawler;
import com.gbsb.api.dto.PostDto;
import com.gbsb.api.entity.Company;
import com.gbsb.api.entity.PostTag;
import com.gbsb.api.entity.enums.CompanyName;
import com.gbsb.api.exception.CrawlException;
import com.gbsb.api.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BaeMinCrawler implements PostCrawler {

    private final CompanyRepository companyRepository;

    @Override
    public List<PostDto> crawlPosts() {
        Company company = companyRepository.findCompanyByName(CompanyName.BAE_MIN.getName()).orElseThrow();

        try {

            Document doc = Jsoup.connect(company.getBlogUrl())
                    .userAgent("Mozilla/5.0")
                    .timeout(10_000)
                    .get();

            Elements links = doc.select("a");

            for (Element link : links) {

            }

        } catch (IOException e) {
            throw new CrawlException(e);
        }



        return List.of();
    }

    @Override
    public CompanyName getCompanyName() {
        return CompanyName.BAE_MIN;
    }


}
