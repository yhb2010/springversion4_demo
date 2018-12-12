package com.version4.chapter17jms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.version4.chapter15.domain.RemoteUser;
import com.version4.chapter17jms.service.PubSender;

@Controller
@RequestMapping("/jms")
public class JmsPubSub {

	@Autowired
	private PubSender pubSender;

	// 针对/users/user的get请求
	@RequestMapping(value = "/pubsub", method = RequestMethod.GET)
	public void listUserByName(@RequestParam("name") String username) {
		RemoteUser member = new RemoteUser(100, username);
		pubSender.send(member);
	}

}
