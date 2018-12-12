package com.version4.chapter11.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.version4.chapter11.domain.QzMember;
import com.version4.mvc.chapter56.exception.DuplicateSpittleException;

@Repository
@Transactional
public class JpaMemberRepositoryImpl implements JpaMemberRepository {

	//注入EntityManager
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<QzMember> findSpittles(int max, int count) {
		return null;
	}

	@Override
	public void save(QzMember spitter, String newName) throws DuplicateSpittleException {
		String update = "update QzMember set userName = " + newName + " where userName = " + spitter.getUserName();
		em.createQuery(update).executeUpdate();
	}

	@Override
	public QzMember findByUserID(Integer userID) {
		return em.find(QzMember.class, userID);
	}

}
