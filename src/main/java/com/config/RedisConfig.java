package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.redis.factory.RedisPool;
import com.redis.factory.RedisPoolFactory;
import com.redis.factory.RedisTemplate;

//redis配置
@Configuration
@PropertySource("classpath:/config/redis.properties")
public class RedisConfig {
	
	@Autowired
	Environment env;
	
	@Bean
	public RedisPool redisPool(){
		RedisPoolFactory factory = new RedisPoolFactory();
		String servers = env.getProperty("redis.servers");
        String masters = env.getProperty("redis.masters");
        String password = env.getProperty("redis.password");
        int database = Integer.parseInt(env.getProperty("redis.database", "0"));
        int maxTotal = Integer.parseInt(env.getProperty("redis.maxTotal", "512"));
        int maxIdle = Integer.parseInt(env.getProperty("redis.maxIdle", "512"));
        int minIdle = Integer.parseInt(env.getProperty("redis.minIdle", "8"));
        int timeout = Integer.parseInt(env.getProperty("redis.timeout", "2000"));
		return factory.initialPool(servers, masters, password, database, maxTotal, maxIdle, minIdle, timeout);
	}
	
	@Bean
	public RedisTemplate redisTemplate(RedisPool redisPool){
		return new RedisTemplate(redisPool, env.getProperty("reids.appName", "default") + ":");
	}

}
