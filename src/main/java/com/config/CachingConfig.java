package com.config;

import net.sf.ehcache.CacheManager;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching//启用缓存
public class CachingConfig {

	@Bean
	//声明缓存管理器，使用EhCache
	public EhCacheCacheManager cacheManager(CacheManager cm){
		return new EhCacheCacheManager(cm);
	}

	@Bean
	public EhCacheManagerFactoryBean ehcache(){
		EhCacheManagerFactoryBean ehCacheFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheFactoryBean.setConfigLocation(new ClassPathResource("config/ehcache.xml"));
		return ehCacheFactoryBean;
	}

}
