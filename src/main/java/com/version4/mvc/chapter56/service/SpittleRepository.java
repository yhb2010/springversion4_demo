package com.version4.mvc.chapter56.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.version4.mvc.chapter56.domain.Spitter;
import com.version4.mvc.chapter56.domain.Spittle;
import com.version4.mvc.chapter56.exception.DuplicateSpittleException;

public interface SpittleRepository {

	List<Spittle> findSpittles(int max, int count);

	//@Cacheable：应用缓存，定义在接口上好一些，这样所有的实现都会应用缓存
	//这个注解用于，在调用被注解的方法时，首先检查当前缓存系统中是否存在键值为key的缓存。如果存在，则直接返回缓存对象，不执行该方法。如果不存在，则调用该方法，并将得到的返回值写入缓存中
	//value：要使用缓存的名称
	//key：自定义缓存的key，支持SpEL表达式：#result方法调用的返回值，其它说明在P378
	//condition：缓存的条件，可以为空，使用SpEL编写，返回true或者false，只有为true才进行缓存，为fasle，则不会将缓存应用到方法调用上
	//unless：如果是true的话，返回值不会放到缓存中，与condition的区别是如果缓存里有值，会用缓存值，但不把返回值放入缓存；condition是完全不应有缓存
	//unless="#result.message.contains('NoCache')"如果返回值的message属性里包含NoCache字符串
	//condition="#spittleId>=10"如果spittleId大于等于10，则应该缓存
	Spittle findOne(int spittleId);

	//@CachePut用于写入缓存，但是与@Cacheable不同，@CachePut注解的方法始终执行，然后将方法的返回值写入缓存，此注解主要用于新增或更新缓存。
	//一般可以用在save方法，但要有返回值用于缓存
	void save(Spitter spitter) throws DuplicateSpittleException;

	Spitter findByUserName(String userName);

	//@CacheEvict移除缓存，可以应用在返回值为void的方法上
	//allEntries：如果为true，特定缓存的所有条目会被删除，如果为false，只有匹配key的条目会被移除
	//beforeInvocation：如果为true，则在方法调用之前移除条目，如果为false，在方法调用之后移除条目
	//public void remove(int spittleId)

}
