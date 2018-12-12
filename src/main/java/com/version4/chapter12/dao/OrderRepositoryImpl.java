package com.version4.chapter12.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.version4.chapter12.domain.Order;

public class OrderRepositoryImpl implements OrderOperations {

	@Autowired
	private MongoOperations mongo;

	@Override
	public List<Order> findOrdersByType(String type) {
		type = type.equals("net") ? "web" : type;

		Criteria where = Criteria.where("type").is(type);
		Query query = Query.query(where);
		return mongo.find(query, Order.class);
	}

}
