package com.system.service;

import com.system.entity.SysUser;
import reactor.core.publisher.Mono;

public interface SysUserService {

        Mono<SysUser> findByUsername(String username);
}