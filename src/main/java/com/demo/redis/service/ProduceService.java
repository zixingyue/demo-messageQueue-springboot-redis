package com.demo.redis.service;

import com.demo.redis.pojo.User;

public interface ProduceService {

	public void produceMsg(User user);
	public void produceStr(String json);
	public void lpush(User user);
}
