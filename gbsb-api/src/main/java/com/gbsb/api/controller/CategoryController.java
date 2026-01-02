package com.gbsb.api.controller;

import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.dto.response.PageResponse;
import jdk.jfr.Category;
import org.hibernate.query.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping("")
    public ResponseEntity<PageResponse<Category>> getCategories(PageRequestDto pageRequestDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryDetail(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }


}
