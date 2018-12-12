package com.version4.chapter11.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.version4.chapter11.domain.QzMember;

//JpaRepository知道这是一个用来持久化QzMember对象的Repository，并且id类型为Integer
//JpaMemberRepositorySpringData接口会自动从JpaRepository里实现一些持久化方法，不用自己写接口实现类
//为了让spring data创建JpaMemberRepositorySpringData实现，需要配置@EnableJpaRepositories
@Repository
public interface JpaMemberRepositorySpringData extends JpaRepository<QzMember, Integer>, JpaMemberAdd {

	//spring data jpa可以直接按方法签名生成方法实现
	QzMember findByUserName(String userName);

	//当jpa提供的默认方法不够用时，可以使用@Query
	@Query("select userName, email from QzMember where email like '%163.com'")
	List<Object> findByLikeEmail();

}
