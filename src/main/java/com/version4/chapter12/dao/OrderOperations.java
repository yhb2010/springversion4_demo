package com.version4.chapter12.dao;

import java.util.List;

import com.version4.chapter12.domain.Order;

public interface OrderOperations {

	List<Order> findOrdersByType(String type);

}
