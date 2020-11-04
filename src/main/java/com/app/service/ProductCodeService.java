package com.app.service;

import com.app.dto.ProductCodeDTO;
import com.app.mappers.ProductCodeMapper;
import com.app.repository.ProductCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCodeService {

    private final ProductCodeRepository productCodeRepository;

    public List<ProductCodeDTO> findAll() {
        return productCodeRepository
                .findAll()
                .stream()
                .map(ProductCodeMapper::toDto)
                .collect(Collectors.toList());
    }
}