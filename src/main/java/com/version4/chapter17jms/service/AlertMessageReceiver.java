package com.version4.chapter17jms.service;

import org.springframework.stereotype.Service;
import com.version4.chapter10.domain.Member;

@Service
//spring提供了pojo的消息处理器
public class AlertMessageReceiver {

	public void getAlert(Member member) {
		System.out.println("接收到一个消息2。");
		try {
			System.out.println("消息内容是2：" + member);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
