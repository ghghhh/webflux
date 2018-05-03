package com.config.securityConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class JsonServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Autowired
    private ObjectMapper mapper;
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpResponse re=webFilterExchange.getExchange().getResponse();
        re.setStatusCode(HttpStatus.OK);
        re.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String,String> map=new HashMap<>();
            map.put("code","500");
            map.put("msg","密码或者用户名错误");
            byte[] b= mapper.writeValueAsBytes(map);
            DataBuffer d= re.bufferFactory().wrap(b);
            return re.writeWith(Mono.just(d));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }
}