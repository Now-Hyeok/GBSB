package com.gbsb.api.controller;

import com.gbsb.api.dto.TagDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("")
    public ResponseEntity<PagedModel<TagDto>> getTags(@Validated @RequestParam PageRequestDto pageRequestDto) {
        return ResponseEntity.ok().body(tagService.getTags(pageRequestDto));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<TagDto>> getPopularTags(int limit) {
        return ResponseEntity.ok().body(tagService.getPopularTags(limit));
    }

}
