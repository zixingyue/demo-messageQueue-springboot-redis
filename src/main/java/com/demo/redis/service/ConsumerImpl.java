package com.demo.redis.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.demo.redis.pojo.User;

@Service
public class ConsumerImpl implements MessageListener{
	
	private static final Logger logger = LoggerFactory.getLogger(ConsumerImpl.class);

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			logger.info("接收到消息---------------------------");
			logger.info(JSON.toJSONString(message));
			logger.info(new String(pattern,"utf-8"));
			byte[] msg= message.getBody();
			String obj = new String(msg,"utf-8"); 
			logger.info(obj);
			String userStr = obj.substring(obj.indexOf(",")+1,obj.length()-1);
			logger.info(userStr);
			User user=JSON.parseObject(userStr,  User.class );
			logger.info(user.toString());
		} catch (Exception e) {
			logger.info("接收消息异常-------------------");
			logger.info(e.getMessage());
		}
		
	}
}
