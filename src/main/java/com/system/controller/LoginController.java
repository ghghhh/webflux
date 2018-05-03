package com.system.controller;

import com.system.entity.SysUser;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    @RequestMapping("/user")
    public Object user(ServerWebExchange exchange){
       Mono<SecurityContext> mo= ReactiveSecurityContextHolder.getContext();
        exchange.getSession().doOnNext(s->s.getAttributes().put("name","cs")).subscribe(s->s.save());
        //return mo.flatMap(s->Mono.just(s.getAuthentication().getPrincipal()));
        return exchange.getSession();
    }

    @RequestMapping("/")
    public Mono<Void> index(ServerWebExchange exchange){
        exchange.getSession().flatMap(s->Mono.just(s.getAttributes().remove("name"))).subscribe(System.out::println);
        URI uri= URI.create("/html/main.html");
        return redirectStrategy.sendRedirect(exchange,uri);
    }

    @GetMapping("/login")
    public Mono<Void> login(ServerWebExchange exchange){

        URI uri= URI.create("/html/index.html");
        return redirectStrategy.sendRedirect(exchange,uri);
    }
}