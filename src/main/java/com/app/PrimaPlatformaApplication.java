package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrimaPlatformaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimaPlatformaApplication.class, args);
    }


    // TODO: 2020-01-12 export orders to XML
    // TODO: 2020-01-12 import products from XLS
    // TODO: 2020-01-12 passwords hassing
    // TODO: 2020-01-12 backups

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