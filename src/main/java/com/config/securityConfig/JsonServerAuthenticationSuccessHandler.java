package com.config.securityConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class JsonServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper mapper;
    private URI location = URI.create("/");

    private ServerRequestCache requestCache = new WebSessionServerRequestCache();
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpResponse re=webFilterExchange.getExchange().getResponse();
        re.setStatusCode(HttpStatus.OK);
        re.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String,String> map=new HashMap<>();
            map.put("code","200");
            requestCache.getRedirectUri(webFilterExchange.getExchange()).defaultIfEmpty(location).subscribe(s->map.put("data",s.getPath()));
            byte[] b= mapper.writeValueAsBytes(map);
            DataBuffer d= re.bufferFactory().wrap(b);
            return re.writeWith(Mono.just(d));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }

    public void setRequestCache(ServerRequestCache requestCache) {
        this.requestCache = requestCache;
    }
}