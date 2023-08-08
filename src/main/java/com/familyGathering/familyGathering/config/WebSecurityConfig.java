package com.familyGathering.familyGathering.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    protected SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/css/styles.css").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/signup").permitAll()
//                        .requestMatchers("/admin").permitAll()

//                        .requestMatchers("/createEvent").permitAll()
//                        .requestMatchers("/myPage/{id}").permitAll()
//                        .requestMatchers("/login").permitAll()
//                        .requestMatchers("/family/{id}").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/myPage")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return http.build();
    }
}
