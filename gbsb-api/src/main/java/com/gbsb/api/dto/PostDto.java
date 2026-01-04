package com.gbsb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String summary;
    private String url;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long companyId;
    private CompanyDto company;
    private Long categoryId;
    private CategoryDto category;
    private List<TagDto> tags;
    private Integer viewCount;
    private String thumbnailUrl;
}
