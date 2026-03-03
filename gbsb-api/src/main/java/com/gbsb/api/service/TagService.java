package com.gbsb.api.service;

import com.gbsb.api.dto.TagDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.dto.response.PageResponse;

import java.util.List;

public interface TagService {
    PageResponse<TagDto> getTags(PageRequestDto pageRequestDto);

    List<TagDto> getPopularTags(int limit);
}