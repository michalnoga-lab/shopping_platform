package com.app.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreateUserResponseDto class
 * Returns ID of newly created user
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateUserResponseDto {

    /**
     * User ID
     */
    private Long id;
}