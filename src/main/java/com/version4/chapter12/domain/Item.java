package com.version4.chapter12.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Item {

	private Long id;
	private String product;
	private double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
