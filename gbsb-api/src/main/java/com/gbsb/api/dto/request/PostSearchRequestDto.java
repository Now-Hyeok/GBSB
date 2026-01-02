package com.gbsb.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchRequestDto {
    String q;
    int page;
    int size;
}
