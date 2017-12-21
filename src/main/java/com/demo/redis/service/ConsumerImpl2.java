package com.demo.redis.service;


import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.demo.redis.pojo.User;

@Service
public class ConsumerImpl2{
	
	private static final Logger logger = LoggerFactory.getLogger(ConsumerImpl2.class);
	
	public void onMessage(String message) {
		//发布订阅模式
		//发送消息是发送的是user对象转换成的字符串String
		try {
			logger.info("接收到消息---------------------------");
			logger.info("message:"+message);	
			message = message.replace("\\", "");
			message = message.substring(1, message.length()-1);
			logger.info("message:"+message);
			//很多时候都执行不到下面的代码就结束了
			User user2=JSON.parseObject(message,User.class );
			logger.info("user2:"+user2.toString());
		} catch (Exception e) {
			logger.info("接收消息异常-------------------");
			logger.info(e.getMessage());
		}
		
	}

	public void onMessage1(Serializable message) {
		//发布订阅模式
		//发送消息是发送的是User对象
		try {
			logger.info("接收到消息---------------------------");
			String obj = message.toString();
			logger.info(obj);
			String userStr = obj.substring(obj.indexOf(",")+1,obj.length()-1);
			logger.info(userStr);
			User user=JSON.parseObject(userStr,User.class );
			logger.info(user.toString());
		} catch (Exception e) {
			logger.info("接收消息异常-------------------");
			logger.info(e.getMessage());
		}
		
	}
}
