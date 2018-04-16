package com.config.securityConfig;

import com.system.service.impl.ReactiveUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 *
 */
@EnableWebFluxSecurity
public class WebfluxSecurityConfig {

    @Bean
    public ReactiveUserDetailsService userDetailsService() {

        return new ReactiveUserDetailsServiceImpl();
    }
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http){
        http.authorizeExchange().pathMatchers("/*").authenticated()
                .and().httpBasic().and().formLogin();
        return http.build();

    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder en=new BCryptPasswordEncoder();
        System.out.println(en.encode("cs"));
    }
}