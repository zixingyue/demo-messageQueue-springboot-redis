package com.demo.redis.service;


import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.demo.redis.pojo.User;

@Service
public class ConsumerImpl3{
	
	private static final Logger logger = LoggerFactory.getLogger(ConsumerImpl3.class);
	
	@Autowired
	RedisTemplate redisTemplate;
	@Value("${redis.lpush.tmp.key}")
	String lpushTempKey;
	@Value("${redis.lpush.key}")
	String lpushKey;
	
	public void onMessage() {
		//生产者消费者模式
		//一直发送请求
		while(true){
			//一直请求
//			Object obj = redisTemplate.opsForList().rightPopAndLeftPush(lpushKey, lpushTempKey);
			
			//每十秒请求一次
			Object obj = redisTemplate.opsForList().rightPopAndLeftPush(lpushKey, lpushTempKey, 10, TimeUnit.SECONDS);
			
			logger.info("3--------------消息处理---------------------");
			//存在数据
			if(null!=obj){
				logger.info("ConsumerImpl3消息处理---------------------");
				String string = obj.toString();
				logger.info("message:"+string);
				
				boolean succ = true;
				if(succ){
					//处理成功
					redisTemplate.opsForList().rightPop(lpushTempKey);
					logger.info("success");
				}else{
					//处理失败
					redisTemplate.opsForList().rightPopAndLeftPush(lpushTempKey, lpushKey);
					logger.info("fail,弹回任务队列");
				}
			}
		}		
	}
}
