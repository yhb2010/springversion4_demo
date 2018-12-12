package com.version4.chapter10.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Member implements Serializable{

	public String userName;
	public String realName;
	public String telPhone;

	public Member() {
		super();
	}
	public Member(String userName, String realName, String telPhone) {
		super();
		this.userName = userName;
		this.realName = realName;
		this.telPhone = telPhone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
