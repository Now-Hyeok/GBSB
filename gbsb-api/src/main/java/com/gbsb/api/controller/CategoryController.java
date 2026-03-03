package com.gbsb.api.controller;

import com.gbsb.api.dto.CategoryDto;
import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.dto.request.PostRequestDto;
import com.gbsb.api.dto.response.ApiSuccessResponse;
import com.gbsb.api.dto.response.PageResponse;
import com.gbsb.api.service.CategoryService;
import com.gbsb.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<ApiSuccessResponse<List<CategoryDto>>> getCategories(PageRequestDto pageRequestDto) {
        return ResponseEntity.ok().body(new ApiSuccessResponse<>(categoryService.getCategories(pageRequestDto).getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CategoryDto>> getCategoryDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(new ApiSuccessResponse<>(categoryService.getCategoryDetail(id)));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PageResponse<PostDto>> getPostsByCategory(@PathVariable Long id, PostRequestDto postRequestDto) {
        return ResponseEntity.ok().body(postService.getPostsByCategory(id, postRequestDto));
    }
}
