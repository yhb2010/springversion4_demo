package com.version4.chapter12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.version4.chapter12.dao.RedisDao;

@Service
public class RedisService {

	@Autowired
	private RedisDao redisDao;

	public void insertKey(String key, String value){
		redisDao.insertKey(key, value);
	}

	public String getKey(String key){
		return redisDao.getKey(key);
	}

}
