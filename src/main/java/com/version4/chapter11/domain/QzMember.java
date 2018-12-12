package com.version4.chapter11.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "qz_member")
public class QzMember {

	//org.hibernate.AnnotationException: No identifier specified for entity异常
	//解决办法：add一个主键进去
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false)
    private int userID;
	@Column(name = "userName", nullable = false)
	private String userName;
	@Column(name = "realName", nullable = true)
	private String realName;
	@Column(name = "telPhone", nullable = true)
	private String telPhone;
	@Column(name = "email", nullable = true)
	private String email;

	public QzMember() {
		super();
	}
	public QzMember(String userName, String realName, String telPhone) {
		super();
		this.userName = userName;
		this.realName = realName;
		this.telPhone = telPhone;
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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
