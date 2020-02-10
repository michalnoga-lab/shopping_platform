package com.app.service;

import com.app.Utilities.CustomPaths;
import com.app.dto.CompanyDTODetailsFromFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class FileServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private FileService fileService;

    @DisplayName("..............")
    @ParameterizedTest
    //@CsvFileSource(resources = "/tests/upload.csv")
    @CsvSource({"a,1", "b,2"})
    void test10(String letter) {

        System.out.println(letter);
        // TODO: 06.02.2020 upload data from file

    }

    @DisplayName("getCompnayDetailsFromFile")
    @ParameterizedTest
    @CsvFileSource(resources = "/files/companies.csv")
    void test20(String a) {

        /*System.out.println("----- jej ----");*/
        /*System.out.println(a);*/

        CompanyDTODetailsFromFile companyDTODetailsFromFile = fileService.getCompanyDetailsFromFile("0000000000");

        System.out.println("++++++ PPPPP +++++++++++");
        System.out.println(companyDTODetailsFromFile);

    }
}