package com.app.service;

import com.app.model.LoggerInfo;
import com.app.repository.LoggerInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class LoggerService {

    private final LoggerInfoRepository loggerInfoRepository;
    private final RequestService requestService;
    private final SecurityService securityService;

    public LoggerInfo add(LoggerInfo loggerInfo) {
        loggerInfo.setTime(LocalDateTime.now());
        loggerInfo.setRemoteAddress(requestService.getRemoteAddress());
        loggerInfo.setUserId(securityService.getLoggedInUserId());

        return loggerInfoRepository.save(loggerInfo);
    }
}