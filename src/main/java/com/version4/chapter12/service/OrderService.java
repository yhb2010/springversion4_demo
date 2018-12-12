package com.version4.chapter12.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.version4.chapter12.dao.OrderDao;
import com.version4.chapter12.domain.Order;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;

	public long getCount(String collectionName){
		return orderDao.getCount(collectionName);
	}

	public List<Order> findAll(){
		return orderDao.findAll();
	}

	public void save(Order order, String collectionName){
		orderDao.save(order, collectionName);
	}

	public List<Order> findByCustomer(String customer){
		return orderDao.findByCustomer(customer);
	}

	public List<Order> findByCustomerAndType(String customer, String type){
		return orderDao.findByCustomerAndType(customer, type);
	}

	public void remove(Order order){
		orderDao.remove(order);
	}

	public Order findByID(String id){
		return orderDao.findByID(id);
	}

}
