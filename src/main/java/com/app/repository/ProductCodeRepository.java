package com.app.repository;

import com.app.model.ProductCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCodeRepository extends JpaRepository<ProductCode, Long> {

    Optional<ProductCode> findByCodeFromOptima(String code);
}