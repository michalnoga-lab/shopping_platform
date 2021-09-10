package com.app.infrastructure.security.controller;

import com.app.infrastructure.controller.dto.ResponseDataDto;
import com.app.infrastructure.security.dto.RefreshTokenDto;
import com.app.infrastructure.security.tokens.AppTokensService;
import com.app.infrastructure.security.tokens.dto.TokensDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityController {

    private final AppTokensService appTokensService;

    @PostMapping("/refresh-tokens")
    public ResponseDataDto<TokensDto> refreshTokens(@RequestBody RefreshTokenDto refreshTokenDto) {
        return null; // TODO
    }
}