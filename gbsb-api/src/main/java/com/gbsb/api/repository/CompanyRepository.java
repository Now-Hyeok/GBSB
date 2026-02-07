package com.gbsb.api.repository;

import com.gbsb.api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long > , CrudRepository<Company,Long> {
    Optional<Company> findCompanyByName(String name);

}
