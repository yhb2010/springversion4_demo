package com.version4.chapter11.dao;

import java.util.List;

//当@Query和JpaRepository提供的查询都不能满足需求时，可以自定义查询方法
public interface JpaMemberAdd {

	List<Object> findByLikeUserName(String email);

}
