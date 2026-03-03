package com.gbsb.api.service;

import com.gbsb.api.dto.CompanyDto;
import com.gbsb.api.dto.request.CompanyRequestDto;
import com.gbsb.api.dto.response.PageResponse;

public interface CompanyService {
    CompanyDto getCompanyDetail(long id);

    PageResponse<CompanyDto> getCompanies(CompanyRequestDto companyRequestDto);
}
