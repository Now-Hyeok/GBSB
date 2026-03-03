package com.gbsb.api.service;

import com.gbsb.api.dto.CategoryDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.dto.response.PageResponse;

public interface CategoryService {

    PageResponse<CategoryDto> getCategories(PageRequestDto pageRequestDto);

    CategoryDto getCategoryDetail(Long id);
}
