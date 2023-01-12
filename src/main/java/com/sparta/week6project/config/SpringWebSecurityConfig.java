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
public class SpringWebSecurityConfig {
    @Bean
    //Creates user registry
    public InMemoryUserDetailsManager configureUsers() throws Exception {
        UserDetails cameron = User.withDefaultPasswordEncoder()
                .username("Cameron")
                .password("secret123")
                .roles("ADMIN")
                .build();
        UserDetails patryk = User.withDefaultPasswordEncoder()
                .username("Patryk")
                .password("passw0rd")
                .roles("BASIC")
                .build();
        return new InMemoryUserDetailsManager(cameron, patryk);

    }

    // Sets up the security policy - authorisation
    @Bean
    public SecurityFilterChain configurePolicy(HttpSecurity http) throws Exception {
        return http.authorizeRequests(auth -> {
                    auth.requestMatchers("/web/employees/update/**")
                            .hasAnyRole("ADMIN","UPDATE");
                    auth.requestMatchers("/web/employees/admin/**")
                            .hasRole("ADMIN");
                    auth.requestMatchers("/web/employees/basic/**")
                            .hasAnyRole("ADMIN","UPDATE","BASIC");
                })
                .formLogin().loginPage("/web/login")
                .successForwardUrl("/web/home")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies()
                .and().build();
    }

}
