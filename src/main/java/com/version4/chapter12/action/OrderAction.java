package com.version4.chapter12.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.version4.chapter12.domain.Order;
import com.version4.chapter12.service.OrderRepositoryService;
import com.version4.chapter12.service.OrderService;
import com.version4.mvc.chapter56.domain.Spittle;
import com.version4.mvc.chapter56.exception.SpittleNotFoundException;

@Controller
@RequestMapping(value = "/order")
public class OrderAction {

	@Autowired
	//private OrderService orderService;
	private OrderRepositoryService orderRepositoryService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Model model){
		model.addAttribute("order", new Order());
		return "order/addOrder";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Order order, Model model){
		orderRepositoryService.save(order, "order");
		return "redirect:/order/listOrder";
	}

	@RequestMapping(value = "/listOrder", method = RequestMethod.GET)
	public String listOrder(Model model){
		model.addAttribute(new Order());
		model.addAttribute(orderRepositoryService.findAll());
		return "order/listOrder";
	}

	@RequestMapping(value = "/searchOrder", method = RequestMethod.POST)
	public String searchOrder(Order order, Model model){
		if(order.getCustomer() != null){
			model.addAttribute(orderRepositoryService.findByCustomer(order.getCustomer()));
		}
		return "order/listOrder";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String spittle(@PathVariable String id, Model model){
		model.addAttribute(orderRepositoryService.findByID(id));
		return "order/showOrder";
	}

}
