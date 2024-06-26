//package com.example.projectshop.security;
//
//
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return encoder;
//
//    }
//
//    // xac thuc
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder.encode("user"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("USER", "ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//
//    @Bean
//    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(rep -> rep
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest()
//                               .permitAll()
////                        .authenticated()
//                )
//                .formLogin(login -> login.loginProcessingUrl("/login"))
//        ;
//
//
//        return http.build();
//    }
//
//
//}
