package com.example.onlinebank.security_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) ->authorize
                        .requestMatchers("/api/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .formLogin(formatLogin -> formatLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login-processing")
                        .defaultSuccessUrl("/dashboard", true))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout"))
                .userDetailsService(userDetailsService);
        return http.build();
    }

}
