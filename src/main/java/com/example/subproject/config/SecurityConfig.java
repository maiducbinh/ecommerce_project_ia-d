// src/main/java/com/example/subproject/config/SecurityConfig.java

package com.example.subproject.config;

import com.example.subproject.entity.Customer;
import com.example.subproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            Optional<Customer> customerOpt = customerRepository.findByUsername(username);
            if(customerOpt.isPresent()){
                Customer customer = customerOpt.get();
                Set<GrantedAuthority> authorities = customer.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet());

                return new User(customer.getUsername(), customer.getPassword(), authorities);
            } else {
                throw new RuntimeException("User not found");
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable() // Tắt CSRF cho đơn giản (không khuyến khích trong môi trường production)
                .authorizeHttpRequests()
                .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/cart/**", "/checkout/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/items", true)
                .permitAll()
                .and()
                .logout()
                .permitAll();
        return http.build();
    }
}
