package com.version4.chapter12.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.version4.chapter12.service.RedisService;

@Controller
@RequestMapping(value = "/redis")
public class RedisAction {

	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam("key") String key, @RequestParam("value") String value, Model model){
		redisService.insertKey(key, value);
		return "redirect:/redis/saveOk/" + key;
	}

	@RequestMapping(value = "/saveOk/{key}", method = RequestMethod.GET)
	public String listOrder(@PathVariable String key, Model model){
		model.addAttribute("obj", redisService.getKey(key));
		return "redis/showkey";
	}

}
