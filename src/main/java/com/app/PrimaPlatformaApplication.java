package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrimaPlatformaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimaPlatformaApplication.class, args);
    }


    // TODO: 2020-01-12 export orders to XML
    // TODO: 2020-01-12 passwords bcrypt + save hash only
    // TODO: 2020-01-12 backups
    // TODO: 16.01.2020 nett and gross cart value
    // TODO: 2020-01-18 prices calculation as nett and gross
    // TODO: 2020-01-19 if product description is null than XML recives optima product description
    // TODO: 2020-01-19 company default price 

    /**
     *
     *
     * Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     *
     * if (principal instanceof UserDetails) {
     *
     *   String username = ((UserDetails)principal).getUsername();
     *
     * } else {
     *
     *   String username = principal.toString();
     *
     * }
     *
     *
     * */
}