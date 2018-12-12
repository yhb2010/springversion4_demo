package com.version4.mvc.chapter56.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Spitter {

	//@AssertFalse：所注解的元素必须是boolean类型，且值为false
	//@AssertTrue：所注解的元素必须是boolean类型，且值为true
	//@DecimalMax：所注解的元素必须是数字，且它的值要小于或等于给定的值
	//@DecimalMin：所注解的元素必须是数字，且它的值要大于或等于给定的值
	//@Digits：所注解的元素必须是数字，且它的值必须有指定的位数
	//@Future：所注解的元素的值必须是一个将来的日期
	//@Past：所注解的元素的值必须是一个已过去的日期
	//@Max：所注解的元素必须是数字，且它的值要小于或等于给定的值
	//@Min：所注解的元素必须是数字，且它的值要大于或等于给定的值
	//@NotNull：所注解的元素的值必须不能为空
	//@Null：所注解的元素的值必须为空
	//@Pattern：所注解的元素的值必须匹配给定的正则表达式
	//@Size：所注解的元素的值必须是Stirg、集合或数组，且它的长度要符合给定的范围
	//message里的{}是占位符，代表了属性文件里的一个属性，ValidationMessages.properties
	@NotNull
	@Size(min=5, max=16, message="{firstName.size}")
	private String firstName;
	@NotNull
	@Size(min=5, max=16, message="{lastName.size}")
	private String lastName;
	@NotNull
	@Size(min=5, max=16, message="{userName.size}")
	private String userName;
	@NotNull
	@Size(min=5, max=16, message="{password.size}")
	private String password;
	private String email;
	private Date birthday;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Spitter other = (Spitter) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
