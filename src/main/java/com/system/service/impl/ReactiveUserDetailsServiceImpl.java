package com.system.service.impl;

import com.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

/**
 *
 */
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return sysUserService.findByUsername(username).flatMap(sysUser -> Mono.just(sysUser));
    }
}