package com.demo.redis.service;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.demo.redis.pojo.User;

@Service
public class ProduceServiceImpl implements ProduceService {

	private static final Logger logger = LoggerFactory.getLogger(ProduceServiceImpl.class);
	
	
	@Autowired
	RedisTemplate redisTemplate;
	
	@Value("${redis.channel}")
	String channel;
	@Value("${redis.lpush.key}")
	String lpushKey;

	@Override
	public void produceMsg(User user) {
		redisTemplate.convertAndSend(channel, user);//发布订阅模式
	}

	@Override
	public void lpush(User user) {
		redisTemplate.opsForList().leftPush(lpushKey, user);
	}

	@Override
	public void produceStr(String json) {
		redisTemplate.convertAndSend(channel, json);
	}
	
}
