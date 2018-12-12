package com.version4.mvc.chapter56.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

//发布的信息对象
public class Spittle {

	public int id;
	public String message;
	public Date time;
	public Double latitude;//发布时对应的经纬度
	public Double longitude;

	public Spittle() {
		super();
	}
	public Spittle(int id, String message) {
		super();
		this.id = id;
		this.message = message;
	}
	public Spittle(int id, String message, Date time) {
		super();
		this.id = id;
		this.message = message;
		this.time = time;
	}
	public Spittle(int id, String message, Date time, Double latitude, Double longitude) {
		super();
		this.id = id;
		this.message = message;
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Spittle other = (Spittle) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
