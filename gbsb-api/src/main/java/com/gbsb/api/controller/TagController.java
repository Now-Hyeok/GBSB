package com.gbsb.api.controller;

import com.gbsb.api.dto.TagsDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.dto.response.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @GetMapping("")
    public ResponseEntity<PageResponse<TagsDto>> getTags( PageRequestDto pageRequestDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<List<TagsDto>> getPopularTags(int limit) {
        return ResponseEntity.ok().build();
    }




}
