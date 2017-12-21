package com.demo.redis.pojo;

import java.io.Serializable;

public class ShortMessage  implements Serializable{

	/** 
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -55006871872797947L;
	private String message;
	private String ie;
	private int type;
	private int num;
	
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
	public String getIe() {
		return ie;
	}
	public void setIe(String ie) {
		this.ie = ie;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
