package com.version4.chapter17jms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.version4.chapter15.domain.RemoteUser;
import com.version4.chapter17jms.service.HelloServiceJms;

@Controller
@RequestMapping("/jms")
public class JmsClient {

	@Autowired
	private HelloServiceJms helloServiceJms;

	@RequestMapping(value = { "/clientSayJms" }, method = RequestMethod.GET)
	public void clientSay(@RequestParam("name") String name) {
		RemoteUser u = helloServiceJms.sayHello2(name);
		System.out.println("jms client:" + u);
	}

}
