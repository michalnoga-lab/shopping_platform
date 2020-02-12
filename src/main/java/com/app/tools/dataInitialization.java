package com.app.tools;

import com.app.model.*;
import com.app.repository.CompanyRepository;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class dataInitialization implements CommandLineRunner { // TODO: 2019-09-23 enable for tests only!

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    Company company1 = Company.builder()
            .name("company 1")
            .nip("0000000000")
            .defaultPrice(Price.NET)
            .paymentInDays(30)
            .active(true)
            .city("city")
            .street("street")
            .streetNumber("10")
            .postCode("1111")
            .build();
    Company company2 = Company.builder()
            .name("company 2")
            .nip("0000000000")
            .defaultPrice(Price.NET)
            .paymentInDays(30)
            .active(true)
            .city("city")
            .street("street")
            .streetNumber("10")
            .postCode("1111")
            .build();

    User user3 = User.builder()
            .login("log")
            .name("name 1")
            .build();
    User user2 = User.builder()
            .login("log2")
            .name("name 2")
            .build();
    User user1 = User.builder()
            .login("user")
            .name("user")
            .role(Role.USER)
            .enabled(true)
            .password("{bcrypt}$2a$10$/HxZgKD8i8uVvtbyMcYPkeeybREyK72tEtVV25OxPvufeUSt9fFEa")
            .build();

    Product product1 = Product.builder().name("Prod1")
            .nettPrice(BigDecimal.valueOf(2.44))
            .vat(23.0)
            .optimaCode(OptimaCode.AMBIPUR)
            .grossPrice(BigDecimal.valueOf(15))
            .build();
    Product product2 = Product.builder().name("Prod2").build();
    Product product3 = Product.builder().name("Prod3").build();

    @Override
    public void run(String... args) throws Exception {

        companyRepository.saveAll(List.of(company1, company2));
        userRepository.saveAll(List.of(user1, user2));

        Company company = companyRepository.findById(1L).get();
        User user = userRepository.findById(3L).get();
        user.setCompany(company);

        companyRepository.saveAndFlush(company);
        userRepository.saveAndFlush(user);

        product1.setCompany(company);

        productRepository.saveAndFlush(product1);
    }
}