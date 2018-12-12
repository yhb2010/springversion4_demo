package com.dynamicproxy;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.version4.chapter10.domain.Member;

@Service
public class DemoService {

	@Autowired
	private SqlSession sqlSession;

	public void addUserAndFind() throws Exception {
		InterfaceRegistry register = new InterfaceRegistry();
		register.addMapper(DemoInterface.class);
		register.addMapper(Class.forName("com.dynamicproxy.DemoInterface"));

		DemoInterface inter = register.getMapper(DemoInterface.class, sqlSession);
		inter.doDefaultWork("张苏磊", 3);
		inter.addUser(new Member("张苏磊2", "123", "13269606741"));
		System.out.println(inter.getByUserName("张苏磊2"));
	}

}
