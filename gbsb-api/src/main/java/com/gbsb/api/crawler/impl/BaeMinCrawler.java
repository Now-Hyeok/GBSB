package com.gbsb.api.crawler.impl;

import com.gbsb.api.crawler.PostCrawler;
import com.gbsb.api.dto.PostDto;
import com.gbsb.api.entity.PostTag;
import com.gbsb.api.entity.enums.CompanyName;

import java.util.List;

public class BaeMinCrawler implements PostCrawler {
    @Override
    public List<PostDto> crawlPosts() {
        return List.of();
    }

    @Override
    public CompanyName getCompanyName() {
        return CompanyName.BAE_MIN;
    }


}
