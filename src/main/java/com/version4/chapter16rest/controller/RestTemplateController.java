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
import org.springframework.web.client.RestTemplate;
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
@RequestMapping(value = "/rest2")
public class RestTemplateController {

	@Autowired
	private RestService restService;

	@RequestMapping(value = "/home2", method = RequestMethod.GET)
	public String home(){
		return "/rest/home2";
	}

	//======get======
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public List<Spittle> spittles(@RequestParam(value = "count", defaultValue = "20") int count){
		RestTemplate rest = new RestTemplate();
		return rest.getForObject("http://localhost/rest/list", List.class);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	//还一个getForEntity与它类似
	public Spittle spittleById(@PathVariable int id){
		RestTemplate rest = new RestTemplate();
		return rest.getForObject("http://localhost/rest/{id}", Spittle.class, id);
	}

	//======put======
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json;charset=UTF-8"})
	public void updateSpittleById(@RequestBody Spittle spittle){
		RestTemplate rest = new RestTemplate();
		rest.put("http://localhost/rest/{id}", spittle, spittle.getId());
	}

	//======delete=====
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteSpittleById(@PathVariable int id){
		RestTemplate rest = new RestTemplate();
		rest.delete("http://localhost/rest/{id}", id);
	}

	//======exchange======
	//可以在发送给服务器端的请求中设置头信息

	//======post=====
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Spittle save(@RequestBody Spittle spittle){
		RestTemplate rest = new RestTemplate();
		return rest.postForObject("http://localhost/rest/save", spittle, Spittle.class);
	}

	//如果想返回新创建资源的位置，可以用postForLocation
	@RequestMapping(value = "/save2", method = RequestMethod.POST, consumes = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String save2(@RequestBody Spittle spittle){
		RestTemplate rest = new RestTemplate();
		return rest.postForLocation("http://localhost/rest/save", spittle, Spittle.class).toString();
	}

}
