package com.app.dto;

import com.app.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeliveryAddressDTO {

    private Long id;
    private String street;
    private String phone;
    private UserDTO userDTO;
}