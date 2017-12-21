package com.demo.redis.pojo;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -5663893354640565613L;
	private long id;
	private String name;
	private int age;
	
	public User(){
		
	}
	public User(long id,String name,int age){
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public User(long id){
		this.id = id;
		this.name = id+"随机";
		this.age = (new Random()).nextInt((int)id);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("id=\"");
		sb.append(this.id);
		sb.append("\",name=\"");
		sb.append(this.name);
		sb.append("\",age=\"");
		sb.append(this.age);
		sb.append("\"]");
		return sb.toString();
	}
	
	
}
