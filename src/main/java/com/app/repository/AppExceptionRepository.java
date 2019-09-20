package com.app.repository;

import com.app.exceptions.AppException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppExceptionRepository extends JpaRepository<AppException, Long> {
}