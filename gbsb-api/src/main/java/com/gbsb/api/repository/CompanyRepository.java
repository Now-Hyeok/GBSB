package com.gbsb.api.repository;

import com.gbsb.api.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyByName(String name);

    Page<Company> findByIsActive(Boolean isActive, Pageable pageable);
}
