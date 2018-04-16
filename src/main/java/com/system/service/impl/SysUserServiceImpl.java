package com.system.service.impl;

import com.system.dao.UserDao;
import com.system.entity.SysUser;
import com.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService{
    @Autowired
    private UserDao userDao;
    @Override
    public SysUser findByUsername(String username) {

        return userDao.findByUsername(username);
    }
}