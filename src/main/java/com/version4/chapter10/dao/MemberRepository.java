package com.version4.chapter10.dao;

import java.util.List;

import com.version4.chapter10.domain.Member;
import com.version4.mvc.chapter56.exception.DuplicateSpittleException;

public interface MemberRepository {

	List<Member> findSpittles(int max, int count);

	void save(Member member, String newName) throws DuplicateSpittleException;

	Member findByUserName(String userName);

}
