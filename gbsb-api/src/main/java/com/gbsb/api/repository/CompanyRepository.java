package com.gbsb.api.repository;

import com.gbsb.api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long > {
}
