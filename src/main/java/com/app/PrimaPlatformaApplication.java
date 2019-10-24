package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrimaPlatformaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimaPlatformaApplication.class, args);
    }


    // TODO: 2019-10-09  XML transforming
    // TODO: 2019-10-09 export / email orders
    // TODO: 2019-10-15 product quantity validation
    // TODO: 2019-10-24 delivery address validator

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