package com.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PrimaPlatformaApplication extends SpringBootServletInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrimaPlatformaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PrimaPlatformaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PrimaPlatformaApplication.class);
    }


    /**
     *
     * package com.example;
     *
     * import java.io.IOException;
     *
     * import javax.ws.rs.container.ContainerRequestContext;
     * import javax.ws.rs.container.ContainerResponseContext;
     * import javax.ws.rs.container.ContainerResponseFilter;
     * import javax.ws.rs.core.MultivaluedMap;
     * import javax.ws.rs.ext.Provider;
     *
     * @Provider
     * public class CORSFilter implements ContainerResponseFilter {
     *
     *     @Override
     *     public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
     *             throws IOException {
     *         System.out.println("FILTER HERE ");
     *         MultivaluedMap<String, Object> headers = responseContext.getHeaders();
     *         headers.add("Access-Control-Allow-Origin", "*"); // Allow Access from everywhere
     *         headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
     *         headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
     *     }
     *
     * }
     *
     *
     * https://stackoverflow.com/questions/59731603/how-to-handle-cors-error-with-an-angular-app-and-a-java-backend
     *
     *
     * */



    // todo zrobić porządnie testy !!!

    // TODO: 17.06.2020 find by session ID

    // TODO: 2020-02-15 cookies info

    // TODO: 14.02.2020 admin - pokaż wszystkich użytkowników bez adminów i super

    // TODO: 14.02.2020 ŁĄCZENIE PRODUKTU Z KODEM OPTIMA - dodać pokaż produkt

    //--------------------------------------------------------------------------------------------

    // TODO: 2020-01-25 invalid login counter
    // TODO: 2020-01-25 http only and secure flags

    // TODO: 28.01.2020 samodzielna rejestracja z kodem

    // TODO: 2020-02-05 vat bez miejsc po przecinku
    // TODO: 2020-02-05 https://www.w3schools.com/cssref/css3_pr_mediaquery.asp - urządzenia mobilne
    // TODO: 10.02.2020 transactional do wszystkich serwisów
    // TODO: 2020-02-12 potwierdzenie zamówienia na email kupującego ???
    // TODO: 2020-02-12 wymuszenie powiązania użytkownika z firmą
}