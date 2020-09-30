package com.app.dto;

import com.app.model.Price;
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
    private String nameShortcut;
    private String nip;
    private String street;
    private String streetNumber;
    private String city;
    private String postCode;
    private Boolean active;
    private Integer paymentInDays;
    private Price defaultPrice;
    private CompanyCodeDTO companyCodeDTO;
}