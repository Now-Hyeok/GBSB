package com.gbsb.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    int page;
    int size;
    long companyId;
    long tagId;
    long categoryId;
    String search;
    String sort;
}
