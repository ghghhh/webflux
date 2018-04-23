package com.system.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.dao.UserDao;
import com.system.entity.SysUser;
import com.system.service.SysUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

/**
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService{

    private Logger logger=LogManager.getLogger(SysUserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private ReactiveRedisTemplate<String,String> redisTemplate;

    @Autowired
    private ObjectMapper mapper;
    private final static String USER="USER_";
    public final static String DEFATLT = "#";
    @Override
    public Mono<SysUser> findByUsername(String username) {
        return redisTemplate.opsForValue().get(USER+username).flatMap(s->{
            SysUser user=null;
            try {
                user=mapper.readValue(s,SysUser.class);
            } catch (IOException e) {
            }
            return Mono.just(user);
        }).switchIfEmpty(
                Mono.defer(()->{
                    SysUser user=userDao.findByUsername(username);
                    if(user==null){
                        logger.warn("找不到用户:{}",username);
                        return redisTemplate.opsForValue().set(USER+username,DEFATLT, Duration.ofHours(1)).then(Mono.empty());
                    }else{
                        logger.info("找到用户:{}",username);
                        try {
                            redisTemplate.opsForValue().set(USER+username,mapper.writeValueAsString(user),Duration.ofHours(1)).subscribe();
                        } catch (JsonProcessingException e) {
                        }
                        return Mono.just(user);
                    }
                })
            );
    }
}