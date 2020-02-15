package com.app.repository;

import com.app.model.LoggerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerInfoRepository extends JpaRepository<LoggerInfo, Long> {
}