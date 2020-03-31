package com.app.controllerExceptions;

import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.model.LoggerInfo;
import com.app.service.LoggerService;
import com.app.service.RequestService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionsRestController {

    private final LoggerService loggerService;
    private final SecurityService securityService;
    private final RequestService requestService;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<LoggerInfo> appExceptionHandler(AppException e) {
        LoggerInfo loggerInfo = LoggerInfo.builder()
                .infoCode(e.getExceptionCode())
                .message(e.getDescription())
                .time(LocalDateTime.now())
                .userId(securityService.getLoggedInUserId())
                .remoteAddress(requestService.getRemoteAddress())
                .build();

        return new ResponseEntity<>(loggerService.add(loggerInfo), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<LoggerInfo> exception(Exception e) {
        LoggerInfo loggerInfo = LoggerInfo.builder()
                .infoCode(InfoCodes.EXCEPTION)
                .message(Arrays.toString(e.getStackTrace()))
                .time(LocalDateTime.now())
                .userId(1L)
                .remoteAddress(requestService.getRemoteAddress())
                .build();

        return new ResponseEntity<>(loggerService.add(loggerInfo), HttpStatus.OK);
    }
}