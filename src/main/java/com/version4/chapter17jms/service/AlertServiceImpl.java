package com.version4.chapter17jms.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import com.version4.chapter10.domain.Member;

@Service
public class AlertServiceImpl implements AlertService {

	@Autowired
	//JmsOperations：JmsTemplate实现此接口
	//Autowired首先根据属性名称匹配，匹配上则置入，如果没有匹配的则匹配类型相同的。这里有多个template，所以要将属性名称和spring配置的bean id保持一致即可
	private JmsOperations jmsTemplate;

	public void sendSpittleAlert(final Member member) {
		System.out.println("---------------生产者发了一个消息：" + member);

		// user.alert.queue指定消息目的地
		// 也可以在bean里面指定默认目的地(<property name="defaultDestinationName" value="user.alert.queue" />)，这样就不需要user.alert.queue了
		// jmsTemplate.send("user.alert.queue", new MessageCreator() {
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				//创建消息
				return session.createObjectMessage(member);
			}
		});
	}

}
