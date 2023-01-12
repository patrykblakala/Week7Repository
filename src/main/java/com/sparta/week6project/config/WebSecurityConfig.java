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

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, update, user);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests().requestMatchers("/").permitAll()
              .and()
                .authorizeHttpRequests().requestMatchers("/web/departments/", "/web/departments/{id}",
                        "/web/salaries/all", "/web/salaries/salary",
                        "/web/departmentManager/all", "/web/departmentManager/show").hasAnyRole("USER",  "UPDATE", "ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers("/web/departments/updateDepartment", "/web/departments/updateSuccess", "/web/departments/createDepartment", "/web/departments/createSuccess", "/web/departments/", "/web/departments/{id}",
                        "/web/salaries/all", "/web/salaries/salary", "/web/salaries/createSalary", "/web/salaries/createSuccess",
                        "/web/departmentManager/all", "/web/departmentManager/show", "/web/departmentManager/update", "/web/departmentManager/create",  "/web/departmentManager/success", "/web/departmentManager/updateSuccess").hasAnyRole("UPDATE", "ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers("/web/departmentManager/**", "/web/departments/**", "/web/salaries/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginProcessingUrl("/");
        return http.build();
    }


}
