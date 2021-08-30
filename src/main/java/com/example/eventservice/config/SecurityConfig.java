package com.example.eventservice.config;

import com.example.eventservice.filter.JwtAuthenticationFilter;
import com.example.eventservice.security.JwtAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public SecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter("/**", this.authenticationManagerBean());
        http
            .csrf().disable()
            .authorizeRequests()
            .and()
            .addFilterBefore(filter, BasicAuthenticationFilter.class)
            .authenticationProvider(jwtAuthenticationProvider)
            .authorizeRequests()
            .anyRequest().authenticated()
            .and().httpBasic();
    }
}
