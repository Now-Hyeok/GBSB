package com.gbsb.api.controller;

import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.entity.Category;
import com.gbsb.api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<PagedModel<Category>> getCategories(@Validated @RequestParam PageRequestDto pageRequestDto) {
        return ResponseEntity.ok().body(categoryService.getCategories(pageRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryDetail( @PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.getCategoryDetail(id));
    }

}
