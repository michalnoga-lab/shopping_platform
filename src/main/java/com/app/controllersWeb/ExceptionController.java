package com.app.controllersWeb;

import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.model.LoggerInfo;
import com.app.service.LoggerInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final LoggerInfoService loggerInfoService;

    @ExceptionHandler(AppException.class)
    public String appExceptionHandler(AppException e) {
        loggerInfoService.add(LoggerInfo.builder()
                .exceptionCode(e.getExceptionCode())
                .exceptionMessage(e.getDescription())
                .time(LocalDateTime.now())
                .build());

        return "exception";
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        loggerInfoService.add(LoggerInfo.builder()
                .exceptionCode(ExceptionCodes.UNKNOWN)
                .exceptionMessage(e.getMessage())
                .time(LocalDateTime.now())
                .build());

        return "exception";
    }
}