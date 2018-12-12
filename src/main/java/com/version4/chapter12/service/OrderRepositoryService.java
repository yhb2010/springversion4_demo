package com.version4.chapter12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.version4.chapter12.dao.OrderDao;
import com.version4.chapter12.dao.OrderRepository;
import com.version4.chapter12.domain.Order;

@Service
public class OrderRepositoryService {

	@Autowired
	private OrderRepository orderRepository;

	public long getCount(String collectionName){
		return orderRepository.count();
	}

	public List<Order> findAll(){
		return orderRepository.findAll();
	}

	public void save(Order order, String collectionName){
		orderRepository.insert(order);
	}

	public List<Order> findByCustomer(String customer){
		return orderRepository.findByCustomer(customer);
	}

	public List<Order> findByCustomerAndType(String customer, String type){
		return orderRepository.findByCustomerAndType(customer, type);
	}

	public void remove(Order order){
		orderRepository.delete(order);
	}

	public Order findByID(String id){
		return orderRepository.findOne(id);
	}

}
