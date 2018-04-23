package com.system.controller;

import com.system.entity.SysUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    @RequestMapping("/user")
    public Object login(){
       // Authentication authentication = new UsernamePasswordAuthenticationToken();

       /* SecurityContextImpl securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        return this.securityContextRepository.save(exchange, securityContext)
                .then(this.authenticationSuccessHandler
                        .onAuthenticationSuccess(webFilterExchange, authentication))
                .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));*/
       Map map=new HashMap();
       Mono<SecurityContext> mo= ReactiveSecurityContextHolder.getContext();

        return mo.flatMap(s->Mono.just(s.getAuthentication().getPrincipal()));
    }

    @RequestMapping("/")
    public Mono<Void> index(ServerWebExchange exchange){

        String context = exchange.getRequest().getPath().contextPath().value();
        URI uri= URI.create(context+"/html/main.html");
        //exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        //exchange.getResponse().getHeaders().setLocation(uri);
        return redirectStrategy.sendRedirect(exchange,uri);
       //return "/html/main.html";
    }
}