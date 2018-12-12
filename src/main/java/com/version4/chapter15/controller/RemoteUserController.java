package com.version4.chapter15.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.version4.chapter15.domain.RemoteUser;
import com.version4.chapter15.service.BurlapRemoteUserService;
import com.version4.chapter15.service.HessianRemoteUserService;
import com.version4.chapter15.service.HttpInvokerRemoteUserService;
import com.version4.chapter15.service.JaxWsUserService;
import com.version4.chapter15.service.RemoteUserService;

/**RMI等RPC中间件技术已广泛应用于各个领域。但是面对规模和复杂度都越来越高的分布式系统，这些技术也显示出其局限性：
（1）同步通信：客户发出调用后，必须等待服务对象完成处理并返回结果后才能继续执行；
（2）客户和服务对象的生命周期紧密耦合：客户进程和服务对象进程 都必须正常运行；如果由于服务对象崩溃或者网络故障导致客户的请求不可达，客户会接收到异常；
（3）点对点通信：客户的一次调用只发送给某个单独的目标对 象。
 * @author dell
 *
 */
@Controller
@RequestMapping(value = "/remote")
public class RemoteUserController {

	@Autowired
	private RemoteUserService remoteUserService;
	@Autowired
	private HessianRemoteUserService hessianRemoteUserService;
	@Autowired
	private BurlapRemoteUserService burlapRemoteUserService;
	@Autowired
	private HttpInvokerRemoteUserService httpInvokerRemoteUserService;
	@Autowired
	private JaxWsUserService jaxWsUserService;

	@RequestMapping(value = "/rmiAll", method = RequestMethod.GET)
	public String register(Model model){
		List<RemoteUser> list = remoteUserService.getAllUser();
		list.forEach(user -> System.out.println(user));
		System.out.println("===================================");
		List<RemoteUser> list2 = hessianRemoteUserService.getAllUser();
		list2.forEach(user -> System.out.println(user));
		System.out.println("===================================");
		List<RemoteUser> list3 = burlapRemoteUserService.getAllUser();
		list3.forEach(user -> System.out.println(user));
		System.out.println("===================================");
		List<RemoteUser> list4 = httpInvokerRemoteUserService.getAllUser();
		list4.forEach(user -> System.out.println(user));
		System.out.println("===================================");
		List<RemoteUser> list5 = jaxWsUserService.getAllUser();
		list5.forEach(user -> System.out.println(user));
		return "remote/xx";
	}

	@RequestMapping(value = "/rmiAll/{userID}", method = RequestMethod.GET)
	public String spittle(@PathVariable int userID, Model model){
		System.out.println(remoteUserService.getUser(userID));
		System.out.println("===================================");
		System.out.println(hessianRemoteUserService.getUser(userID));
		System.out.println("===================================");
		System.out.println(burlapRemoteUserService.getUser(userID));
		System.out.println("===================================");
		System.out.println(httpInvokerRemoteUserService.getUser(userID));
		System.out.println("===================================");
		System.out.println(jaxWsUserService.getUser(userID));
		return "remote/xx";
	}

}
