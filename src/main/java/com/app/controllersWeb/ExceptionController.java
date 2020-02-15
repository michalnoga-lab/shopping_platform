package com.app.controllersWeb;

import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.model.LoggerInfo;
import com.app.service.LoggerService;
import com.app.service.RequestService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final LoggerService loggerService;
    private final SecurityService securityService;
    private final RequestService requestService;

    @ExceptionHandler({AppException.class})
    public String appExceptionHandler(AppException e) {
        loggerService.add(LoggerInfo.builder()
                .infoCode(e.getExceptionCode())
                .message(e.getDescription())
                .time(LocalDateTime.now())
                .userId(securityService.getLoggedInUserId())
                .remoteAddress(requestService.getRemoteAddress())
                .build());

        return "exception";
    }

    @ExceptionHandler({Exception.class})
    public String exception(Exception e) {
        loggerService.add(LoggerInfo.builder()
                .infoCode(InfoCodes.UNKNOWN)
                .message(e.getMessage())
                .time(LocalDateTime.now())
                .userId(securityService.getLoggedInUserId())
                .remoteAddress(requestService.getRemoteAddress())
                .build());

        return "exception";
    }
}