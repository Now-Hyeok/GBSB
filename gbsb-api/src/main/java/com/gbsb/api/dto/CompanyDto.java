package com.gbsb.api.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyDto {

    private Long id;

    private String name;
    private String nameKo;
    private String slug;
    private String logoUrl;
    private String blogUrl;

}
