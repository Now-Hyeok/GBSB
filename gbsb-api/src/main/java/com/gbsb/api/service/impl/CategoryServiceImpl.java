package com.gbsb.api.service.impl;

import com.gbsb.api.dto.CategoryDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.entity.Category;
import com.gbsb.api.repository.CategoryRepository;
import com.gbsb.api.service.CategoryService;
import com.gbsb.api.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.gbsb.api.dto.response.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponse<CategoryDto> getCategories(PageRequestDto pageRequestDto) {
        int pageSize = pageRequestDto.getSize() > 0 ? pageRequestDto.getSize() : 20;
        String sortField = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        if ("count".equalsIgnoreCase(pageRequestDto.getSort())) {
            sortField = "name";  // count is @Transient
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(
            pageRequestDto.getPage(),
            pageSize,
            Sort.by(direction, sortField)
        );

        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        Page<CategoryDto> dtoPage = categoryPage.map(cat -> modelMapper.map(cat, CategoryDto.class));
        return new PageResponse<>(dtoPage);
    }

    @Override
    public CategoryDto getCategoryDetail(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
        return modelMapper.map(category, CategoryDto.class);
    }
}
