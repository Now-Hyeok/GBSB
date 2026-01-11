package com.gbsb.api.service.impl;

import com.gbsb.api.dto.TagDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.repository.TagRepository;
import com.gbsb.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public PagedModel<TagDto> getTags(PageRequestDto pageRequestDto) {
        return null;
    }

    @Override
    public List<TagDto> getPopularTags(int limit) {
        return List.of();
    }
}
