package com.app.repository;

import com.app.model.CompanyCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyCodeRepository extends JpaRepository<CompanyCode, Long> {
}