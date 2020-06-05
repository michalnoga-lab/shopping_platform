package com.app.dto;

import com.app.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDTO {

    private Long id;
    private String login;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String passwordConfirmation;
    private Boolean enabled;
    private Role role;
    private CompanyDTO companyDTO;
}