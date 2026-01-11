package com.gbsb.api.controller;

import com.gbsb.api.dto.CompanyDto;
import com.gbsb.api.dto.request.CompanyRequestDto;
import com.gbsb.api.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/")
    public ResponseEntity<PagedModel<CompanyDto>> getCompanies(@Validated @RequestParam CompanyRequestDto companyRequestDto) {
        return ResponseEntity.ok().body(companyService.getCompanies(companyRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyDetail(@PathVariable long id) {
        return ResponseEntity.ok().body(companyService.getCompanyDetail(id));
    }
}
