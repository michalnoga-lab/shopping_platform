//package com.app.tools;
//
//import com.app.model.*;
//import com.app.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.id.IdentifierGeneratorHelper;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class dataInitialization implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final CompanyRepository companyRepository;
//    private final ProductRepository productRepository;
//    private final ProductCodeRepository productCodeRepository;
//    private final DeliveryAddressRepository deliveryAddressRepository;
//
//    Company company1 = Company.builder()
//            .name("company 1")
//            .nip("0000000000")
//            .defaultPrice(Price.NET)
//            .paymentInDays(30)
//            .active(true)
//            .city("city")
//            .street("street")
//            .streetNumber("10")
//            .postCode("1111")
//            .nameShortcut("prima")
//            .build();
//
//    User user3 = User.builder()
//            .login("super@gmail.com")
//            .email("super@gmail.com")
//            .name("super")
//            .role(Role.SUPER)
//            .enabled(true)
//            .password("{bcrypt}$2a$10$/HxZgKD8i8uVvtbyMcYPkeeybREyK72tEtVV25OxPvufeUSt9fFEa")
//            .build();
//
//    User user2 = User.builder()
//            .login("admin@gmail.com")
//            .email("admin@gmail.com")
//            .name("admin")
//            .role(Role.ADMIN)
//            .enabled(true)
//            .password("{bcrypt}$2a$10$/HxZgKD8i8uVvtbyMcYPkeeybREyK72tEtVV25OxPvufeUSt9fFEa")
//            .build();
//
//    User user1 = User.builder()
//            .login("user@gmail.com")
//            .email("user@gmail.com")
//            .name("user")
//            .role(Role.USER)
//            .enabled(true)
//            .password("{bcrypt}$2a$10$/HxZgKD8i8uVvtbyMcYPkeeybREyK72tEtVV25OxPvufeUSt9fFEa")
//            .build();
//
//    User user4 = User.builder()
//            .login("root@gmail.com")
//            .email("root@gmail.com")
//            .name("root")
//            .surname("root")
//            //.company(Company.builder().nameShortcut("prima").build())
//            //.company(company1)
//            .role(Role.ROOT)
//            .enabled(true)
//            .password("{bcrypt}$2a$10$/HxZgKD8i8uVvtbyMcYPkeeybREyK72tEtVV25OxPvufeUSt9fFEa")
//            .build();
//
//    ProductCode code1 = ProductCode
//            .builder()
//            .codeFromOptima("optima code")
//            .build();
//
//    Product product1 = Product.builder().name("Prod1")
//            .nettPrice(BigDecimal.valueOf(2.44))
//            .vat(23.0)
//            .hidden(false)
//            .grossPrice(BigDecimal.ONE)
//            .auctionIndex("AAA")
//            .company(company1)
//            .description("DESCR")
//            .numberInAuction("num in auction")
//            .grossPrice(BigDecimal.valueOf(15))
//            .hidden(false)
//            .productCode(code1)
//            .build();
//
//    Product product2 = Product.builder().name("Prod2")
//            .nettPrice(BigDecimal.valueOf(2.44))
//            .vat(23.0)
//            .hidden(false)
//            .grossPrice(BigDecimal.ONE)
//            .auctionIndex("AAA")
//            .company(company1)
//            .description("DESCR")
//            .numberInAuction("num in auction")
//            .grossPrice(BigDecimal.valueOf(15))
//            .productCode(code1)
//            .hidden(false)
//            .build();
//
//    DeliveryAddress deliveryAddress1 = DeliveryAddress
//            .builder()
//            .street("street")
//            .hidden(false)
//            .phone("phone")
//            .user(user1)
//            .build();
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        // ------------------------------------------------------------------------------------
//
//        companyRepository.save(company1);
//        deliveryAddressRepository.save(deliveryAddress1);
//
//        productCodeRepository.save(code1);
//
//        user1.setCompany(company1);
//        user2.setCompany(company1);
//        user3.setCompany(company1);
//        user4.setCompany(company1);
//
//        // TODO: 21.02.2020 for production only
//        if (userRepository.findAll().size() == 0) {
//            //userRepository.save(user4);
//            //userRepository.save(user3);
//            userRepository.save(user2);
//            userRepository.save(user1);
//        }
//        // ------------------------------------------------------------------------------------
//
//
//        // ------------------------------------------------------------------------------------
//        // TODO: 21.02.2020 for tests only
//
//        productRepository.save(product1);
//        productRepository.save(product2);
//
//
////        User user = userRepository.findById(3L).get();
////        user.setCompany(company1);
//
//
//        // ------------------------------------------------------------------------------------
//
//    }
//}