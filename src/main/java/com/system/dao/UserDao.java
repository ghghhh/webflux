package com.system.dao;

import com.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select user_name as username , user_password as password from sys_user where user_name=#{username}")
    SysUser findByUsername(String username);
}