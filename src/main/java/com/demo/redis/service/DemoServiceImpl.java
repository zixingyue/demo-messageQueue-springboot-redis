package com.demo.redis.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo.redis.pojo.User;

@Service
public class DemoServiceImpl implements DemoService {

	@Override
	@Cacheable(value = "usercache",keyGenerator = "keyGenerator")  
    public User findUser(long id){  
        System.out.println("findUser------无缓存的时候调用这里");  
        return new User(id);  
    } 

}
