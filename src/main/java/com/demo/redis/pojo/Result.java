package com.demo.redis.pojo;

import java.io.Serializable;

public class Result implements Serializable{

	/** 
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 7442924209132197855L;
	private int code;
	private boolean result;
	private String message;
	private String error;
	private Object data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
