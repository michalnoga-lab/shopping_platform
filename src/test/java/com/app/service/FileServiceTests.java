package com.app.service;

import com.app.dto.CompanyDTODetailsFromFile;
import com.app.dto.ProductCodeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.security.cert.CertSelector;
import java.util.List;

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
    @CsvFileSource(resources = "/tests/companies.csv")
    void test20() {
        //stays empty
    }

    @DisplayName("getCompanyDetailsFromFile - find company in file")
    @ParameterizedTest
    @CsvFileSource(resources = "/tests/companies.csv")
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

//    @DisplayName("getProductsCodeFromFile - read content of file")
//    @ParameterizedTest
//    @CsvFileSource(resources = "/tests/companies.csv")
//    void test30() {
//
//        List<ProductCodeDTO> productsCodeFromFile = fileService.getProductsCodeFromFile();
//
//
//
//    }
    // TODO: 23.04.2020 kasujemy ??? dane mają przychodzić z JS 
}