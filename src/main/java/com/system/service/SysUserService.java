package com.system.service;

import com.system.entity.SysUser;

public interface SysUserService {

        SysUser findByUsername(String username);
}