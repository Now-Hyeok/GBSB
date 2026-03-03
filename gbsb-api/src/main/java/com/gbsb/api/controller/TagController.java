package com.gbsb.api.controller;

import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.TagDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.dto.request.PostRequestDto;
import com.gbsb.api.dto.response.ApiSuccessResponse;
import com.gbsb.api.dto.response.PageResponse;
import com.gbsb.api.service.PostService;
import com.gbsb.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<ApiSuccessResponse<List<TagDto>>> getTags(PageRequestDto pageRequestDto) {
        return ResponseEntity.ok().body(new ApiSuccessResponse<>(tagService.getTags(pageRequestDto).getContent()));
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiSuccessResponse<List<TagDto>>> getPopularTags(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(new ApiSuccessResponse<>(tagService.getPopularTags(limit)));
    }

    @GetMapping("/{tagSlug}/posts")
    public ResponseEntity<PageResponse<PostDto>> getPostsByTag(@PathVariable String tagSlug, PostRequestDto postRequestDto) {
        return ResponseEntity.ok().body(postService.getPostsByTag(tagSlug, postRequestDto));
    }
}
