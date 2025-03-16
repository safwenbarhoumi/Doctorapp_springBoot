package tn.esprit.tp1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .csrf().disable();
        return http.build();
                /*
                .authorizeRequests()
                .requestMatchers("/doctor/signup", "/patient/signup", "/admin/signup", "/patient/login", "/doctor/login", "/admin/login", "/conversations/start","/conversations/send-message", "/conversations/user/**", "/doctor/complete-profile","/doctor/**")
                .permitAll() // Allow signup and login endpoints without authentication
                .anyRequest()
                .authenticated() // Require authentication for other endpoints
                .and()
                .csrf().disable(); // Disable CSRF for testing purposes (you should configure CSRF properly in production)
        return http.build();*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Return a BCryptPasswordEncoder for password encoding
    }
}

/*
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}*/