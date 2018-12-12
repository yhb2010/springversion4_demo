package com.version4.chapter12.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.config.MongoConfig;
import com.version4.chapter12.domain.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfig.class})
public class OrderRepositorySpringDataTest {

	@Autowired
	private OrderRepository orderRepository;

	@Test
	public void findOne(){
		int count = orderRepository.countByCustomer("张苏磊");
		System.out.println(count);
	}

	@Test
	public void findByCustomerLike(){
		List<Order> members = orderRepository.findByCustomerLike("陈丽");
		members.forEach(o -> System.out.println(o));
	}

	@Test
	public void findByCustomerLikeAndType(){
		List<Order> members = orderRepository.findByCustomerLikeAndType("陈丽", "web");
		members.forEach(o -> System.out.println(o));
	}

	@Test
	public void findZhangOrders(){
		List<Order> members = orderRepository.findZhangOrders("web");
		members.forEach(o -> System.out.println(o));
	}

	@Test
	public void findOrdersByType(){
		List<Order> members = orderRepository.findOrdersByType("net");
		members.forEach(o -> System.out.println(o));
	}

}
