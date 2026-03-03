package com.gbsb.api.service.impl;

import com.gbsb.api.dto.TagDto;
import com.gbsb.api.dto.request.PageRequestDto;
import com.gbsb.api.dto.response.PageResponse;
import com.gbsb.api.entity.Tag;
import com.gbsb.api.repository.TagRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    @DisplayName("태그 목록 조회")
    void getTags() {
        PageRequestDto requestDto = new PageRequestDto();
        requestDto.setPage(0);
        requestDto.setSize(50);

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Spring");
        tag.setSlug("spring");

        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setName("Spring");
        tagDto.setSlug("spring");

        Page<Tag> tagPage = new PageImpl<>(List.of(tag));

        when(tagRepository.findAll(any(Pageable.class))).thenReturn(tagPage);
        when(modelMapper.map(any(Tag.class), eq(TagDto.class))).thenReturn(tagDto);

        PageResponse<TagDto> result = tagService.getTags(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Spring");
    }

    @Test
    @DisplayName("인기 태그 조회")
    void getPopularTags() {
        Object[] row = {1L, "Spring", "spring", 42L};
        List<Object[]> rows = new ArrayList<>();
        rows.add(row);
        when(tagRepository.findPopularTagsNative(10)).thenReturn(rows);

        List<TagDto> result = tagService.getPopularTags(10);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Spring");
        assertThat(result.get(0).getCount()).isEqualTo(42);
    }
}
