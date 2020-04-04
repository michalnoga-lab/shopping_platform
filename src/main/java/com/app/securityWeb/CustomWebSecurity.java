package com.app.securityWeb;

import com.app.securityRest.JWTAuthenticationEntryPoint;
import com.app.securityRest.JWTAuthenticationFilter;
import com.app.securityRest.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class CustomWebSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public CustomWebSecurity(
            @Qualifier("userDetailsServiceImpl")
                    UserDetailsService userDetailsService,
            JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors()

                .and()
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/js/**").permitAll()
                .antMatchers("/", "/cookies", "/rodo").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ROOT", "SUPER", "ADMIN")
                .antMatchers("/super/**").hasAnyRole("ROOT", "SUPER")
                .antMatchers("/root/**").hasAnyRole("ROOT")

                .anyRequest().authenticated()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // TODO: 2020-03-29 enable for web use only
                .and()
                .formLogin()
                .loginPage("/security/login").permitAll()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/security/failed")

                .and()
                .logout().permitAll()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .logoutSuccessUrl("/security/loggedOut").permitAll()

                .and()
                .exceptionHandling().accessDeniedPage("/security/accessDenied")
                .accessDeniedHandler(accessDeniedHandler())

                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse,
                               AccessDeniedException e) throws IOException, ServletException {
                httpServletResponse.sendRedirect("/accessDenied");
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http//localhost:3000")); // TODO: 2020-03-29 update in production
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}