package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompanyDTODetailsFromFile {

    private String code;
    private String name;
    private String nip;
    private String postalCode;
    private String city;
    private String street;
}