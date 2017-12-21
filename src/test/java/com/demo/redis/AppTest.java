package com.demo.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.redis.pojo.User;
import com.demo.redis.service.ConsumerImpl3;
import com.demo.redis.service.DemoService;
import com.demo.redis.service.ProduceService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoRedisApp.class)
public class AppTest {

	@Autowired
	ProduceService pro;
	@Autowired
	ConsumerImpl3 consumer;
	@Autowired
	DemoService demo;
	
	@Test
	public void rpop(){
		consumer.onMessage();
	}
	
	@Test
	public void lpush(){
		User user = new User(20);
		String json = JSON.toJSONString(user);
		System.out.println(json);	
		pro.lpush(user);
	}
	
	@Test
	public void produceStr(){
		User user = new User(20);
		String json = JSON.toJSONString(user);
		System.out.println(json);	
		pro.produceStr(json);
	}
	
	@Test
	public void produceUser(){
		User user = new User(20);
		out(user);
		pro.produceMsg(user);
	}
	
	@Test
	public void cache(){
		out(demo.findUser(13));
		out(demo.findUser(15));
	}
	
	
	
	public void out(Object object){
		System.out.println("***********************结果***************************");
		System.out.println(JSONObject.toJSONString(object));
	}
}
