package com.system.service.impl;

import com.system.dao.UserDao;
import com.system.entity.SysUser;
import com.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 */
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        SysUser user=sysUserService.findByUsername(username);
        return user==null?Mono.empty():Mono.just(user);
    }
}