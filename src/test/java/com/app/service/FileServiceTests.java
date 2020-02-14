package com.app.service;

import com.app.dto.CompanyDTODetailsFromFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@AutoConfigureMockMvc
@SpringBootTest
public class FileServiceTests {

    @SpyBean
    private FileService fileService;

    @DisplayName("getProductsFromFile")
    @ParameterizedTest
    @CsvFileSource(resources = "/tests/upload.csv")
    void test10() {
        //stays empty
    }

    @DisplayName("getCompanyDetailsFromFile - read content of file")
    @ParameterizedTest
    void test20() {
        //stays empty
    }

    @DisplayName("getCompanyDetailsFromFile - find company in file")
    @ParameterizedTest
    @CsvFileSource(resources = "/files/companies.csv")
    void test21() {

        CompanyDTODetailsFromFile expectedResult = CompanyDTODetailsFromFile.builder()
                .code("TESTCODE")
                .name("TESTNAME")
                .nip("0000000000")
                .postalCode("TESTPOSTALCODE")
                .city("TESTCITY")
                .street("TESTSTREET")
                .build();

        CompanyDTODetailsFromFile actualResult = fileService.getCompanyDetailsFromFile("0000000000");

        Assertions.assertEquals(expectedResult, actualResult);
    }
}