package com.version4.chapter17jms.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.version4.chapter10.domain.Member;
import com.version4.chapter17jms.service.AlertService;

/**jms即Java消息服务（Java Message Service）应用程序接口是一个Java平台中关于面向消息中间件（MOM）的API，用于在两个应用程序之间，或分布式系统中发送消息，进行异步通信。Java消息服务是一个与具体平台无关的API，绝大多数MOM提供商都对JMS提供支持。
 * 发送者将消息发送给消息服务器，消息服务器将消息存放在若干队列中，在合适的时候再将消息转发给接收者。这种模式下，发送和接收是异步的，发送者无需等待；二者的生命周期未必相同：发送消息的时候接收者不一定运行，接收消息的时候发送者也不一定运行； 一对多通信：对于一个消息可以有多个接收者。
 * @author dell
 *
 */
@Controller
@RequestMapping("/jms")
public class Jms {

	@Resource(name="alertServiceImpl")
	private AlertService alertService;

	// 针对/users/user的get请求
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public void listUserByName(@RequestParam("name") String username) {
		for (int i = 1; i <= 5; i++) {
			Member member = new Member(username, username + i, "13269606741");
			alertService.sendSpittleAlert(member);
		}
	}

}
