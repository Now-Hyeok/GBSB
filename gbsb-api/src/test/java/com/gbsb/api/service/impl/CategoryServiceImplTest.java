package com.gbsb.api.service.impl;

import com.gbsb.api.dto.CategoryDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.dto.response.PageResponse;
import com.gbsb.api.entity.Category;
import com.gbsb.api.exception.CategoryNotFoundException;
import com.gbsb.api.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("카테고리 목록 조회")
    void getCategories() {
        PageRequestDto requestDto = new PageRequestDto();
        requestDto.setPage(0);
        requestDto.setSize(20);

        Category category = new Category();
        category.setId(1L);
        category.setName("개발");
        category.setSlug("development");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("개발");
        categoryDto.setSlug("development");

        Page<Category> categoryPage = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(categoryDto);

        PageResponse<CategoryDto> result = categoryService.getCategories(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("개발");
    }

    @Test
    @DisplayName("카테고리 상세 조회 - 존재하는 카테고리")
    void getCategoryDetail_found() {
        Category category = new Category();
        category.setId(1L);
        category.setName("개발");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("개발");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(categoryDto);

        CategoryDto result = categoryService.getCategoryDetail(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("개발");
    }

    @Test
    @DisplayName("카테고리 상세 조회 - 존재하지 않는 카테고리")
    void getCategoryDetail_notFound() {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategoryDetail(999L))
                .isInstanceOf(CategoryNotFoundException.class);
    }
}
