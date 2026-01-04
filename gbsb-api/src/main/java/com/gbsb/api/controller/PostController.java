package com.gbsb.api.controller;

import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.request.PostRequestDto;
import com.gbsb.api.dto.request.PostSearchRequestDto;
import com.gbsb.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<PagedModel<PostDto>> getPosts(PostRequestDto postRequestDto) {
        return ResponseEntity.ok().body(postService.getPosts(postRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.getPostDetail(id));
    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<PagedModel<PostDto>> getPostsByCompanies(@PathVariable Long companyId, PostRequestDto postRequestDto) {
        return ResponseEntity.ok().body(postService.getPostsByCompany(companyId, postRequestDto));
    }

    @GetMapping("/tags/{tagSlug}")
    public ResponseEntity<PagedModel<PostDto>> getPostsByTags(@PathVariable String tagSlug, PostRequestDto postRequestDto) {
        return ResponseEntity.ok().body(postService.getPostsByTag(tagSlug, postRequestDto));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<PagedModel<PostDto>> getPostsByCategory(@PathVariable Long categoryId, PostRequestDto postRequestDto) {
        return ResponseEntity.ok().body(postService.getPostsByCategory(categoryId, postRequestDto));
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<PostDto>> postsSearch(PostSearchRequestDto searchRequestDto) {
        return ResponseEntity.ok().body(postService.searchPosts(searchRequestDto));
    }

}
