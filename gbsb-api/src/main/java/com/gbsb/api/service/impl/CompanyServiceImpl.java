package com.gbsb.api.service.impl;

import com.gbsb.api.dto.CompanyDto;
import com.gbsb.api.dto.request.CompanyRequestDto;
import com.gbsb.api.entity.Company;
import com.gbsb.api.repository.CompanyRepository;
import com.gbsb.api.service.CompanyService;
import com.gbsb.api.exception.CompanyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.gbsb.api.dto.response.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Override
    public CompanyDto getCompanyDetail(long id) {
        Company company = companyRepository.findById(id)
            .orElseThrow(() -> new CompanyNotFoundException(id));
        return modelMapper.map(company, CompanyDto.class);
    }

    @Override
    public PageResponse<CompanyDto> getCompanies(CompanyRequestDto companyRequestDto) {
        int pageSize = companyRequestDto.getSize() > 0 ? companyRequestDto.getSize() : 20;
        Pageable pageable = PageRequest.of(
            companyRequestDto.getPage(),
            pageSize,
            Sort.by(Sort.Direction.ASC, "name")
        );

        Page<Company> companyPage;
        if (companyRequestDto.isActive()) {
            companyPage = companyRepository.findByIsActive(true, pageable);
        } else {
            companyPage = companyRepository.findAll(pageable);
        }

        Page<CompanyDto> dtoPage = companyPage.map(company -> modelMapper.map(company, CompanyDto.class));
        return new PageResponse<>(dtoPage);
    }
}
