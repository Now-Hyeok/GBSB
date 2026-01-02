package com.gbsb.api.controller;

import com.gbsb.api.dto.CompanyDto;
import com.gbsb.api.dto.request.CompanyRequestDto;
import com.gbsb.api.dto.response.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/companies")
public class CompanyController {

    @GetMapping("/")
    public ResponseEntity<PageResponse<CompanyDto>> getCompanies(@RequestParam CompanyRequestDto companyRequestDto) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyDetail(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }
}
