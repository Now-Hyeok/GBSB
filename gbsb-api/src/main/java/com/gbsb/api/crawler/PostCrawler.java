package com.gbsb.api.crawler;


import com.gbsb.api.dto.PostDto;
import com.gbsb.api.entity.enums.CompanyName;

import java.util.List;

public interface PostCrawler {

    /**
     * 게시글 목록 수집
     */
    List<PostDto> crawlPosts();

    CompanyName getCompanyName();


}