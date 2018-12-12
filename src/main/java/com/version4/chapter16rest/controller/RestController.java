package com.version4.chapter16rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.version4.chapter16rest.service.RestService;
import com.version4.mvc.chapter56.domain.Spittle;

/*
 * /blog/1　HTTP　GET　=>　　得到id　=　1的blog
/blog/1　HTTP　DELETE　=>　删除　id　=　1的blog
/blog/1　HTTP　PUT　=>　　更新id　=　1的blog
/blog　　　HTTP　POST　=>　　新增BLOG
 * */
//@RestController使用这个，连@ResponseBody都不用写了
@Controller
@RequestMapping(value = "/rest")
public class RestController {

	@Autowired
	private RestService restService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(){
		return "/rest/home";
	}

	//@ResponseBody告诉spring我们要将返回的对象作为资源发送给客户端，并将其转换为客户端可接受的表述形式。
	//produces = {"application/json"}表明该方法只处理预期输出为json的请求
	//HTTP 406错误是HTTP协议状态码的一种，表示无法使用请求的内容特性来响应请求的网页。一般是指客户端浏览器不接受所请求页面的 MIME 类型。
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public List<Spittle> spittles(@RequestParam(value = "count", defaultValue = "20") int count){
		return restService.spittles(count);
	}

	//HTTP 400 Bad Request：1、对象类型与model类型不一致；2、model类型不能为private，应为protected/public
	//HTTP 415：需要contentType : 'application/json'
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = {"application/json;charset=UTF-8"})
	@ResponseBody
	//public ResponseEntity<Spittle> saveSpittle(@RequestBody Spittle spittle){
		//return restService.save(spittle);
	//}
	//上面的写法过于简单，只能返回200，不能看出资源是否创建成功，应该返回ResponseEntity，可以包含201(created)状态码
	public ResponseEntity<Spittle> saveSpittle(@RequestBody Spittle spittle, UriComponentsBuilder ucb){
		Spittle spittle1 = restService.save(spittle);
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/spittle/").path(String.valueOf(spittle1.getId())).build().toUri();
		headers.setLocation(locationUri);
		ResponseEntity<Spittle> responseEntity = new ResponseEntity<Spittle>(spittle1, headers, HttpStatus.CREATED);
		return responseEntity;
	}

	//ResponseEntity是@ResponseBody的替代方案，控制器方法可以返回ResponseEntity对象，ResponseEntity中可以包含响应相关的元数据以及要转换成资源表述的对象
	//spittle为null的情况，也可以统一用@ExceptionHandler处理
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> spittleById(@PathVariable int id){
		Spittle spittle = restService.spittleById(id);
		if(spittle == null){
			com.version4.chapter16rest.domain.Error error = new com.version4.chapter16rest.domain.Error(4, "Spittle [" + id + "] not found");
			return new ResponseEntity<com.version4.chapter16rest.domain.Error>(error, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Spittle>(spittle, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteSpittleById(@PathVariable int id){
		restService.delete(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json;charset=UTF-8"})
	public void updateSpittleById(@RequestBody Spittle spittle){
		restService.updateSpittleById(spittle);
	}

}
