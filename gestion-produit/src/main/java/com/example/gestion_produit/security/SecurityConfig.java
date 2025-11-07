package com.example.gestion_produit.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Autoriser toutes les requêtes sans authentification
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                // Désactiver CSRF (utile pour les tests Postman)
                .csrf(csrf -> csrf.disable())
                // Pas besoin de formulaire de login
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
