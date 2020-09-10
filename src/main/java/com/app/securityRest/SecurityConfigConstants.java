package com.app.securityRest;

public interface SecurityConfigConstants {

    String SECRET = "SecurityPhraseToGenerateToken"; // TODO: 24.03.2020 !!!
    long EXPIRATION_TIME = 864000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}