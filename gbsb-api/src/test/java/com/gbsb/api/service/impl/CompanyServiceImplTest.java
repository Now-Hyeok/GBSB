package com.gbsb.api.service.impl;

import com.gbsb.api.dto.CompanyDto;
import com.gbsb.api.dto.request.CompanyRequestDto;
import com.gbsb.api.dto.response.PageResponse;
import com.gbsb.api.entity.Company;
import com.gbsb.api.exception.CompanyNotFoundException;
import com.gbsb.api.repository.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    @DisplayName("기업 목록 조회 - 전체")
    void getCompanies_all() {
        CompanyRequestDto requestDto = new CompanyRequestDto();
        requestDto.setPage(0);
        requestDto.setSize(20);

        Company company = createTestCompany(1L, "Kakao");
        Page<Company> companyPage = new PageImpl<>(List.of(company));
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setName("Kakao");

        when(companyRepository.findAll(any(Pageable.class))).thenReturn(companyPage);
        when(modelMapper.map(any(Company.class), eq(CompanyDto.class))).thenReturn(companyDto);

        PageResponse<CompanyDto> result = companyService.getCompanies(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Kakao");
    }

    @Test
    @DisplayName("기업 상세 조회 - 존재하는 기업")
    void getCompanyDetail_found() {
        Company company = createTestCompany(1L, "Toss");
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setName("Toss");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(modelMapper.map(any(Company.class), eq(CompanyDto.class))).thenReturn(companyDto);

        CompanyDto result = companyService.getCompanyDetail(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Toss");
    }

    @Test
    @DisplayName("기업 상세 조회 - 존재하지 않는 기업")
    void getCompanyDetail_notFound() {
        when(companyRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.getCompanyDetail(999L))
                .isInstanceOf(CompanyNotFoundException.class);
    }

    private Company createTestCompany(Long id, String name) {
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setNameKo(name + " KO");
        company.setSlug(name.toLowerCase());
        company.setBlogUrl("https://" + name.toLowerCase() + ".tech");
        company.setIsActive(true);
        return company;
    }
}
