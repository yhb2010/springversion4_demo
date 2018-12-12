package com.dynamicproxy;

import com.version4.chapter10.domain.Member;

public interface DemoInterface {

	default public void doDefaultWork(String s, int i){
		System.out.println("DoSomeOtherWork implementation in the interface: " + s + "," + i);
	}

	public void addUser(Member member);

	public Member getByUserName(String userName);

}
