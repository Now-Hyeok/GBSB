package com.gbsb.api.service;

import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.request.PostRequestDto;
import com.gbsb.api.dto.request.PostSearchRequestDto;
import org.springframework.data.web.PagedModel;

public interface PostService {

    PagedModel<PostDto> getPosts(PostRequestDto requestDto);

    PostDto getPostDetail(Long id);

    PagedModel<PostDto> getPostsByCompany(Long companyId, PostRequestDto requestDto);

    PagedModel<PostDto> getPostsByTag(String tagSlug, PostRequestDto requestDto);

    PagedModel<PostDto> getPostsByCategory(Long categoryId, PostRequestDto requestDto);

    PagedModel<PostDto> searchPosts(PostSearchRequestDto requestDto);
}
