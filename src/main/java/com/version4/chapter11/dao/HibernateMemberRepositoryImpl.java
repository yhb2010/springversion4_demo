package com.version4.chapter11.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.version4.chapter11.domain.QzMember;
import com.version4.mvc.chapter56.exception.DuplicateSpittleException;

@Repository
public class HibernateMemberRepositoryImpl implements MemberRepository {

	@Autowired
	//可以再包装一层，不和JdbcTemplate耦合
	private SessionFactory sessionFactory;

	@Override
	public List<QzMember> findSpittles(int max, int count) {
		return null;
	}

	@Override
	//hibernate4不支持hibernate3的 getcurrentSession，建议用openSession
	public void save(QzMember spitter, String newName) throws DuplicateSpittleException {
		Query query = sessionFactory.openSession().createQuery("update QzMember set userName = " + newName + " where userName = " + spitter.getUserName());
		query.executeUpdate();
	}

	@Override
	public QzMember findByUserName(String userName) {
		return (QzMember)sessionFactory.openSession().createCriteria(QzMember.class).add(Restrictions.eq("userName", userName)).list().get(0);
	}

}
