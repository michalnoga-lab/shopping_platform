package com.app.application.service;

import com.app.domain.cart.dto.GetCartDto;
import com.app.domain.cart.repository.CartRepository;
import com.app.domain.verification_token.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final VerificationTokenRepository verificationTokenRepository;

//    public List<GetCartDto> findAll() {
//
//    }
    // TODO
}