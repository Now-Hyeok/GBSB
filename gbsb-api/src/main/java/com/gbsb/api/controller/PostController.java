package com.gbsb.api.controller;

import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.request.PostRequestDto;
import com.gbsb.api.dto.request.PostSearchRequestDto;
import com.gbsb.api.dto.response.PageResponse;
import com.gbsb.api.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {


    private final PostsService postsService;

    @GetMapping("")
    public ResponseEntity<PageResponse<PostDto>> getPosts(PostRequestDto postRequestDto) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostDetail(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<PageResponse<PostDto>> getPostsByCompanies(@PathVariable Long companyId, PostRequestDto postRequestDto) {
        return ResponseEntity.ok().build();

    }

    @GetMapping("/tags/{tagSlug}")
    public ResponseEntity<PageResponse<PostDto>> getPostsByTags(@PathVariable String tagSlug,  PostRequestDto postRequestDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<PageResponse<PostDto>> getPostsByCategory(@PathVariable Long categoryId,  PostRequestDto postRequestDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<PostDto>> postsSearch(PostSearchRequestDto searchRequestDto) {
        return ResponseEntity.ok().build();
    }

}
