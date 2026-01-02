package com.gbsb.api.dto;

import com.gbsb.api.common.PostCategory;
import com.gbsb.api.common.PostTag;
import com.gbsb.api.domain.Company;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String url;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CompanyDto company;
    private PostCategory category;
    private List<PostTag> tags;
    private int viewCount;
    private String thumbnailUrl;

}
