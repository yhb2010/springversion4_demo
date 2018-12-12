package com.version4.chapter11.dao;

import java.util.List;
import com.version4.chapter11.domain.QzMember;
import com.version4.mvc.chapter56.exception.DuplicateSpittleException;

public interface MemberRepository {

	List<QzMember> findSpittles(int max, int count);

	void save(QzMember member, String newName) throws DuplicateSpittleException;

	QzMember findByUserName(String userName);

}
