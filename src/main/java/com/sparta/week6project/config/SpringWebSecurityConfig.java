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
public class    SpringWebSecurityConfig {
    @Bean
    //Creates user registry
    public InMemoryUserDetailsManager configureUsers() throws Exception {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        UserDetails update = User.withDefaultPasswordEncoder()
                .username("update")
                .password("update")
                .roles("UPDATE")
                .build();
        UserDetails basic = User.withDefaultPasswordEncoder()
                .username("basic")
                .password("basic")
                .roles("BASIC")
                .build();
        return new InMemoryUserDetailsManager(admin, update, basic);

    }

    // Sets up the security policy - authorisation
    @Bean
    public SecurityFilterChain configurePolicy(HttpSecurity http) throws Exception {
        return http.authorizeRequests(auth -> {
                    auth.requestMatchers("/web/employees/admin/**",
                                    "/web/salaries/admin/**",
                                    "/web/departments/admin/**",
                                    "/web/departmentManager/admin/**",
                                    "/web/titles/admin/**",
                                    "/web/users/admin/**")
                            .hasRole("ADMIN");

                    auth.requestMatchers("/web/employees/update/**",
                                    "/web/salaries/update/**",
                                    "/web/departments/update/**",
                                    "/web/departmentManager/update/**",
                                    "/web/titles/update/**",
                                    "/web/users/update/**")
                            .hasAnyRole("ADMIN","UPDATE");

                    auth.requestMatchers("/web/employees/basic/**",
                                    "/web/salaries/basic/**",
                                    "/web/departments/basic/**",
                                    "/web/departmentManager/basic/**",
                                    "/web/titles/basic/**",
                                    "/web/users/basic/**")
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
                .and()
                .rememberMe()
                .and()
                .build();
    }

}
