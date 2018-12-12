package com.version4.chapter15.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class RemoteUser implements Serializable{

	private int userID;
	private String userName;

	public RemoteUser() {
		super();
	}
	public RemoteUser(int userID, String userName) {
		super();
		this.userID = userID;
		this.userName = userName;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
