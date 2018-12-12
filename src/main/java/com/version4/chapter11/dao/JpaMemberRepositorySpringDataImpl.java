package com.version4.chapter11.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

//当spring data jpa为Repository生成接口时，它还会查找名字与接口相同，并且添加了Impl后缀的类，如果存在，则把它的方法与spring data jpa所生成的方法合并到一起
@Repository
public class JpaMemberRepositorySpringDataImpl implements JpaMemberAdd {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Object> findByLikeUserName(String email) {
		String query = "select userName, email from QzMember where email like '%" + email + "'";
		return em.createQuery(query).getResultList();
	}

}
