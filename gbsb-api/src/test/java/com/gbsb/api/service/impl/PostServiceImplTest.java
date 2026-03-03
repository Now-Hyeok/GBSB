package com.gbsb.api.service.impl;

import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.request.PostRequestDto;
import com.gbsb.api.dto.request.PostSearchRequestDto;
import com.gbsb.api.dto.response.PageResponse;
import com.gbsb.api.entity.Post;
import com.gbsb.api.exception.PostNotFoundException;
import com.gbsb.api.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    @DisplayName("포스트 목록 조회 - 기본 (필터 없음)")
    void getPosts_noFilter() {
        PostRequestDto requestDto = new PostRequestDto();
        requestDto.setPage(0);
        requestDto.setSize(12);

        Post post = createTestPost(1L, "Test Post");
        Page<Post> postPage = new PageImpl<>(List.of(post));

        when(postRepository.findAll(any(Pageable.class))).thenReturn(postPage);

        PageResponse<PostDto> result = postService.getPosts(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getPage()).isEqualTo(0);
    }

    @Test
    @DisplayName("포스트 목록 조회 - 기업별 필터링")
    void getPosts_byCompany() {
        PostRequestDto requestDto = new PostRequestDto();
        requestDto.setPage(0);
        requestDto.setSize(12);
        requestDto.setCompanyId(1L);

        Post post = createTestPost(1L, "Company Post");
        Page<Post> postPage = new PageImpl<>(List.of(post));

        when(postRepository.findByCompanyId(eq(1L), any(Pageable.class))).thenReturn(postPage);

        PageResponse<PostDto> result = postService.getPosts(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("포스트 상세 조회 - 존재하는 포스트")
    void getPostDetail_found() {
        Post post = createTestPost(1L, "Detail Post");
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostDto result = postService.getPostDetail(1L);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Detail Post");
    }

    @Test
    @DisplayName("포스트 상세 조회 - 존재하지 않는 포스트")
    void getPostDetail_notFound() {
        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.getPostDetail(999L))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    @DisplayName("포스트 검색")
    void searchPosts() {
        PostSearchRequestDto searchDto = new PostSearchRequestDto();
        searchDto.setQ("Spring");
        searchDto.setPage(0);
        searchDto.setSize(12);

        Post post = createTestPost(1L, "Spring Boot Guide");
        Page<Post> postPage = new PageImpl<>(List.of(post));

        when(postRepository.findByTitleContainingOrSummaryContaining(eq("Spring"), eq("Spring"), any(Pageable.class)))
                .thenReturn(postPage);

        PageResponse<PostDto> result = postService.searchPosts(searchDto);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("태그별 포스트 조회")
    void getPostsByTag() {
        PostRequestDto requestDto = new PostRequestDto();
        requestDto.setPage(0);
        requestDto.setSize(12);

        Post post = createTestPost(1L, "Tagged Post");
        Page<Post> postPage = new PageImpl<>(List.of(post));

        when(postRepository.findByTagsSlug(eq("spring"), any(Pageable.class))).thenReturn(postPage);

        PageResponse<PostDto> result = postService.getPostsByTag("spring", requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    private Post createTestPost(Long id, String title) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setSummary("Test summary");
        post.setUrl("https://example.com/post/" + id);
        post.setPublishedAt(LocalDateTime.now());
        post.setViewCount(0);
        return post;
    }
}
