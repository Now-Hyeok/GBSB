package com.gbsb.api.service.impl;

import com.gbsb.api.dto.TagDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.entity.Tag;
import com.gbsb.api.repository.TagRepository;
import com.gbsb.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.gbsb.api.dto.response.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponse<TagDto> getTags(PageRequestDto pageRequestDto) {
        int pageSize = pageRequestDto.getSize() > 0 ? pageRequestDto.getSize() : 50;
        String sortField = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        if ("count".equalsIgnoreCase(pageRequestDto.getSort())) {
            sortField = "name";  // count is @Transient, fall back to name
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(
            pageRequestDto.getPage(),
            pageSize,
            Sort.by(direction, sortField)
        );

        Page<Tag> tagPage = tagRepository.findAll(pageable);
        Page<TagDto> dtoPage = tagPage.map(tag -> modelMapper.map(tag, TagDto.class));
        return new PageResponse<>(dtoPage);
    }

    @Override
    public List<TagDto> getPopularTags(int limit) {
        int effectiveLimit = limit > 0 ? limit : 10;
        List<Object[]> results = tagRepository.findPopularTagsNative(effectiveLimit);
        List<TagDto> tags = new ArrayList<>();

        for (Object[] row : results) {
            TagDto dto = new TagDto();
            dto.setId(((Number) row[0]).longValue());
            dto.setName((String) row[1]);
            dto.setSlug((String) row[2]);
            dto.setCount(((Number) row[3]).intValue());
            tags.add(dto);
        }

        return tags;
    }
}
