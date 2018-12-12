package com.version4.chapter12.domain;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//标识映射到mongodb文档上的领域对象
@Document
public class Order {

	//标识某个域为id域
	@Id
	private String id;
	private String customer;
	private String type;
	private List<Item> items;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
