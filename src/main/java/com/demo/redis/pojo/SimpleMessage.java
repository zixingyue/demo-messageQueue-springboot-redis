package com.demo.redis.pojo;

import java.io.Serializable;

public class SimpleMessage  implements Serializable{

	/** 
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4993548158627952034L;
	private String message;
	private int type;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
