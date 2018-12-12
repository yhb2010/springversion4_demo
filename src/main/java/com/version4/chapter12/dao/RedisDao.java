package com.version4.chapter12.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.redis.factory.RedisTemplate;

@Repository
public class RedisDao {

	@Autowired
	private RedisTemplate redisTemplate;

	public void insertKey(String key, String value){
		redisTemplate.set(key, value);
	}

	public String getKey(String key){
		return redisTemplate.get(key);
	}

}
