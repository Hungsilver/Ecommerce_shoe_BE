package com.example.projectshop.config;

import com.example.projectshop.security.UserDetailServiceCustom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceCustom();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        AuthenticationManager manager = builder.build();

        http
                .cors().disable()
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeHttpRequests()
//                .requestMatchers("/admin/**").hasAnyRole("ADMIN", "USER")
//                .requestMatchers("/user/**").hasRole("USER")

                .requestMatchers("/api/customer/login").permitAll()
                .requestMatchers("/api/customer/register").permitAll()
                .requestMatchers("/auth/admin/register-account").permitAll()
                .requestMatchers("/auth/admin/login").permitAll()
                .requestMatchers("/auth/admin/**").hasAnyAuthority("ADMIN", "STAFF")
                .anyRequest().permitAll()
//                .anyRequest().authenticated()
                .and()
                .authenticationManager(manager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}