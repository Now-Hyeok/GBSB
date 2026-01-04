package com.gbsb.api.controller;

import com.gbsb.api.dto.request.PageRequestDto;
import jdk.jfr.Category;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping("")
    public ResponseEntity<PagedModel<Category>> getCategories(PageRequestDto pageRequestDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryDetail(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

}
