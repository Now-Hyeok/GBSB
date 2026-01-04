package com.gbsb.api.controller;

import com.gbsb.api.dto.TagDto;
import com.gbsb.api.dto.request.PageRequestDto;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @GetMapping("")
    public ResponseEntity<PagedModel<TagDto>> getTags(PageRequestDto pageRequestDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<List<TagDto>> getPopularTags(int limit) {
        return ResponseEntity.ok().build();
    }

}
