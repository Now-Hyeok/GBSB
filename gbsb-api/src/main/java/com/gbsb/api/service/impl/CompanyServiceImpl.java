package com.gbsb.api.service.impl;

import com.gbsb.api.dto.CompanyDto;
import com.gbsb.api.dto.request.CompanyRequestDto;
import com.gbsb.api.repository.CompanyRepository;
import com.gbsb.api.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public CompanyDto getCompanyDetail(long id) {
        return null;
    }


    @Override
    public PagedModel<CompanyDto> getCompanies(CompanyRequestDto companyRequestDto) {
        return null;
    }
}
