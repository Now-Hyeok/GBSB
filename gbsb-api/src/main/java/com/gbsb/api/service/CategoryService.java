package com.gbsb.api.service;

import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.entity.Category;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface CategoryService {

    PagedModel<Category> getCategories(PageRequestDto pageRequestDto);

    Category getCategoryDetail(Long id);

}
