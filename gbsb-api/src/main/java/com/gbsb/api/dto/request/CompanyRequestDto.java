package com.gbsb.api.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequestDto {

    private int page;
    private int size;
    private boolean isActive;

}
