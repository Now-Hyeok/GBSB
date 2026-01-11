package com.gbsb.api.service.impl;

import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.entity.Category;
import com.gbsb.api.repository.CategoryRepository;
import com.gbsb.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public PagedModel<Category> getCategories(PageRequestDto pageRequestDto) {
        return null;
    }

    @Override
    public Category getCategoryDetail(Long id) {
        return null;
    }
}
