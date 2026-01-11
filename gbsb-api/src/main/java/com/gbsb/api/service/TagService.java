package com.gbsb.api.service;

import com.gbsb.api.dto.TagDto;
import com.gbsb.api.dto.request.PageRequestDto;
import org.jspecify.annotations.Nullable;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface TagService {
    PagedModel<TagDto> getTags(PageRequestDto pageRequestDto);

    List<TagDto> getPopularTags(int limit);
}