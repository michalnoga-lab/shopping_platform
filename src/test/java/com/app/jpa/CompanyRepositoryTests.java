package com.app.jpa;

import com.app.model.Company;
import com.app.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CompanyRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("save company to DB")
    public void test1() {

        Company company1 = Company.builder().name("Company 1").build();
        Company company2 = Company.builder().name("Company 2").build();
        Company company3 = Company.builder().name("Company 3").build();

        testEntityManager.persist(company1);
        testEntityManager.persist(company2);
        testEntityManager.persist(company3);

        Assertions.assertEquals(3, companyRepository.findAll().size());
    }
}