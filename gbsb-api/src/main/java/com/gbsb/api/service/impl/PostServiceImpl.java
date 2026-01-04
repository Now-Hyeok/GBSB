package com.gbsb.api.service.impl;

import com.gbsb.api.entity.Post;
import com.gbsb.api.dto.CategoryDto;
import com.gbsb.api.dto.CompanyDto;
import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.TagDto;
import com.gbsb.api.dto.request.PostRequestDto;
import com.gbsb.api.dto.request.PostSearchRequestDto;
import com.gbsb.api.exception.PostNotFoundException;
import com.gbsb.api.repository.PostRepository;
import com.gbsb.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PagedModel<PostDto> getPosts(PostRequestDto requestDto) {
        Pageable pageable = createPageable(requestDto.getPage(), requestDto.getSize(), requestDto.getSort());

        Page<Post> postPage;

        if (requestDto.getCompanyId() > 0) {
            postPage = postRepository.findByCompanyId(requestDto.getCompanyId(), pageable);
        } else if (requestDto.getCategoryId() > 0) {
            postPage = postRepository.findByCategoryId(requestDto.getCategoryId(), pageable);
        } else if (requestDto.getTagId() > 0) {
            postPage = postRepository.findByTagsId(requestDto.getTagId(), pageable);
        } else if (requestDto.getSearch() != null && !requestDto.getSearch().isEmpty()) {
            postPage = postRepository.findByTitleContainingOrSummaryContaining(
                requestDto.getSearch(),
                requestDto.getSearch(),
                pageable
            );
        } else {
            postPage = postRepository.findAll(pageable);
        }

        return convertToPagedModel(postPage);
    }

    @Override
    @Transactional
    public PostDto getPostDetail(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);

        post.setViewCount(post.getViewCount() + 1);

        return convertToDto(post);
    }

    @Override
    public PagedModel<PostDto> getPostsByCompany(Long companyId, PostRequestDto requestDto) {
        Pageable pageable = createPageable(requestDto.getPage(), requestDto.getSize(), requestDto.getSort());
        Page<Post> postPage = postRepository.findByCompanyId(companyId, pageable);

        return convertToPagedModel(postPage);
    }

    @Override
    public PagedModel<PostDto> getPostsByTag(String tagSlug, PostRequestDto requestDto) {
        Pageable pageable = createPageable(requestDto.getPage(), requestDto.getSize(), requestDto.getSort());
        Page<Post> postPage = postRepository.findByTagsSlug(tagSlug, pageable);

        return convertToPagedModel(postPage);
    }

    @Override
    public PagedModel<PostDto> getPostsByCategory(Long categoryId, PostRequestDto requestDto) {
        Pageable pageable = createPageable(requestDto.getPage(), requestDto.getSize(), requestDto.getSort());
        Page<Post> postPage = postRepository.findByCategoryId(categoryId, pageable);

        return convertToPagedModel(postPage);
    }

    @Override
    public PagedModel<PostDto> searchPosts(PostSearchRequestDto requestDto) {
        Pageable pageable = PageRequest.of(
            requestDto.getPage(),
            requestDto.getSize() > 0 ? requestDto.getSize() : 12,
            Sort.by(Sort.Direction.DESC, "publishedAt")
        );

        Page<Post> postPage = postRepository.findByTitleContainingOrSummaryContaining(
            requestDto.getQ(),
            requestDto.getQ(),
            pageable
        );

        return convertToPagedModel(postPage);
    }

    private Pageable createPageable(int page, int size, String sort) {
        int pageSize = size > 0 ? size : 12;

        Sort sortOrder;
        if ("popular".equalsIgnoreCase(sort)) {
            sortOrder = Sort.by(Sort.Direction.DESC, "viewCount");
        } else {
            sortOrder = Sort.by(Sort.Direction.DESC, "publishedAt");
        }

        return PageRequest.of(page, pageSize, sortOrder);
    }

    private PagedModel<PostDto> convertToPagedModel(Page<Post> postPage) {
        Page<PostDto> dtoPage = postPage.map(this::convertToDto);
        return new PagedModel<>(dtoPage);
    }

    private PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setSummary(post.getSummary());
        dto.setUrl(post.getUrl());
        dto.setPublishedAt(post.getPublishedAt());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setViewCount(post.getViewCount());
        dto.setThumbnailUrl(post.getThumbnailUrl());

        if (post.getCompany() != null) {
            dto.setCompanyId(post.getCompany().getId());
            CompanyDto companyDto = modelMapper.map(post.getCompany(), CompanyDto.class);
            dto.setCompany(companyDto);
        }

        if (post.getCategory() != null) {
            dto.setCategoryId(post.getCategory().getId());
            CategoryDto categoryDto = modelMapper.map(post.getCategory(), CategoryDto.class);
            dto.setCategory(categoryDto);
        }

        if (post.getTags() != null && !post.getTags().isEmpty()) {
            List<TagDto> tagDtos = post.getTags().stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
            dto.setTags(tagDtos);
        }

        return dto;
    }
}
