package com.version4.chapter12.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.version4.chapter12.domain.Order;

@Repository
public class OrderDao {

	//MongoTemplate实现了这个接口
	@Autowired
	private MongoOperations mongo;

	public long getCount(String collectionName){
		return mongo.getCollection(collectionName).count();
	}

	public List<Order> findAll(){
		return mongo.findAll(Order.class);
	}

	public void save(Order order, String collectionName){
		mongo.save(order, collectionName);
	}

	public List<Order> findByCustomer(String customer){
		return mongo.find(Query.query(Criteria.where("customer").is(customer)), Order.class);
	}

	public List<Order> findByCustomerAndType(String customer, String type){
		return mongo.find(Query.query(Criteria.where("customer").is(customer).and("type").is(type)), Order.class);
	}

	public void remove(Order order){
		mongo.remove(order);
	}

	public Order findByID(String id){
		return mongo.findById(id, Order.class);
	}

}
