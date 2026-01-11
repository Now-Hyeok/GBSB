package com.gbsb.api.service;

import com.gbsb.api.dto.CompanyDto;
import com.gbsb.api.dto.request.CompanyRequestDto;
import org.jspecify.annotations.Nullable;
import org.springframework.data.web.PagedModel;

public interface CompanyService {
    CompanyDto getCompanyDetail(long id);

    PagedModel<CompanyDto> getCompanies(CompanyRequestDto companyRequestDto);
}
