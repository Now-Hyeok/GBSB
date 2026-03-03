package com.gbsb.api.service;

import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.request.PostRequestDto;
import com.gbsb.api.dto.request.PostSearchRequestDto;
import com.gbsb.api.dto.response.PageResponse;

import java.util.List;

public interface PostService {

    PageResponse<PostDto> getPosts(PostRequestDto requestDto);

    PostDto getPostDetail(Long id);

    PageResponse<PostDto> getPostsByCompany(Long companyId, PostRequestDto requestDto);

    PageResponse<PostDto> getPostsByTag(String tagSlug, PostRequestDto requestDto);

    PageResponse<PostDto> getPostsByCategory(Long categoryId, PostRequestDto requestDto);

    PageResponse<PostDto> searchPosts(PostSearchRequestDto requestDto);

    void insertPost(PostDto postDto);

    void insertAllPosts(List<PostDto> postDtos);
}
