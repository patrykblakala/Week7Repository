package com.sparta.week6project.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests().requestMatchers("/").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/web/departmentManager/**").hasRole("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers("/web/departments/**").hasRole("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers("/web/salaries/**").hasRole("ADMIN")
                .and()
                .formLogin()
//                .loginPage("/login");
                .loginProcessingUrl("/");
//                .successForwardUrl("/");

        return http.build();
    }


}
