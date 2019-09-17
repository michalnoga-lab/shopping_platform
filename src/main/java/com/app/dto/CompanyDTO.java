package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompanyDTO {

    private Long id;
    private String name;
    private String nip;
    private String street;
    private String streetNumber;
    private String city;
    private String postCode;
}