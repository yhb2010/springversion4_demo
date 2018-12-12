package com.version4.chapter12.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.version4.chapter12.domain.Order;

public interface OrderRepository extends MongoRepository<Order, String>, OrderOperations {

	List<Order> findByCustomer(String customer);
	int countByCustomer(String customer);
	List<Order> findByCustomerLike(String customer);
	List<Order> findByCustomerAndType(String customer, String type);
	List<Order> findByCustomerLikeAndType(String customer, String type);
	@Query("{'customer' : '张苏磊', 'type' : '?0'}")
	List<Order> findZhangOrders(String type);

}
