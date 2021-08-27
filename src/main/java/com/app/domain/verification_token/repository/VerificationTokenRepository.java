package com.app.domain.verification_token.repository;

import com.app.domain.verification_token.VerificationToken;

import java.util.Optional;

/**
 * VerificationTokenRepository interface
 */
public interface VerificationTokenRepository {
    Optional<VerificationToken> findByToken(String token);
}