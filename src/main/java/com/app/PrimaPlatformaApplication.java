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
    // TODO: 2020-01-19 if product description is null than XML recives optima product description
    // TODO: 22.01.2020 contact form - ideas
    // TODO: 2020-01-25 invalid login counter
    // TODO: 2020-01-25 http only and secure flags
    // TODO: 28.01.2020 samodzielna rejestracja z kodem
    // TODO: 04.02.2020 w bazie hash hasła a nie jego szyfrogram
    // TODO: 04.02.2020 ukrywanie wszysktich produktów danej firmy

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