package com.app.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class FileServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @ParameterizedTest
    //@CsvFileSource(resources = "/tests/upload.csv")
    @CsvSource({"a,1", "b,2"})
    void test10(String letter) {

        System.out.println(letter);
        // TODO: 06.02.2020 upload data from file

    }
}