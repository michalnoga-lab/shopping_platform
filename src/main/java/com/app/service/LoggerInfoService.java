package com.app.service;

import com.app.model.LoggerInfo;
import com.app.repository.LoggerInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggerInfoService {

    private final LoggerInfoRepository loggerInfoRepository;

    public void add(LoggerInfo loggerInfo) {
        loggerInfoRepository.save(loggerInfo);
    }
}