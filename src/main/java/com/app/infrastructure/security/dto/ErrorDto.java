package com.app.infrastructure.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error DTO class
 * Custom security error class as DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorDto {

    /**
     * Message returned as error to frontend
     */
    private String message;
}