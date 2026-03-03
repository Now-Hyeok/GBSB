package com.gbsb.api.controller;

import com.gbsb.api.dto.CompanyDto;
import com.gbsb.api.dto.PostDto;
import com.gbsb.api.dto.request.CompanyRequestDto;
import com.gbsb.api.dto.request.PostRequestDto;
import com.gbsb.api.dto.response.ApiSuccessResponse;
import com.gbsb.api.dto.response.PageResponse;
import com.gbsb.api.service.CompanyService;
import com.gbsb.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<PageResponse<CompanyDto>> getCompanies(CompanyRequestDto companyRequestDto) {
        return ResponseEntity.ok().body(companyService.getCompanies(companyRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CompanyDto>> getCompanyDetail(@PathVariable long id) {
        return ResponseEntity.ok().body(new ApiSuccessResponse<>(companyService.getCompanyDetail(id)));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PageResponse<PostDto>> getPostsByCompany(@PathVariable Long id, PostRequestDto postRequestDto) {
        return ResponseEntity.ok().body(postService.getPostsByCompany(id, postRequestDto));
    }
}
