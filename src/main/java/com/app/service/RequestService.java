package com.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {

    private final HttpServletRequest request;

    public String getRemoteAddress() {
        String remoteAddress = "internal";

        if (request != null) {
            remoteAddress = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddress == null || remoteAddress.length() == 0) {
                remoteAddress = request.getRemoteAddr();
            }
        }
        if (remoteAddress.equals("0:0:0:0:0:0:0:1")) {
            return "localhost";
        }

        return remoteAddress;
    }
}