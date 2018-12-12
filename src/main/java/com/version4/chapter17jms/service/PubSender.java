package com.version4.chapter17jms.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import com.version4.chapter15.domain.RemoteUser;

/**发布者
 * @author dell
 *
 */
@Service
public class PubSender {

	@Autowired
	private JmsOperations myJmsTemplate;

	public void send(final RemoteUser member) {
		System.out.println("---------------发布了一个消息：" + member);

		myJmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				//创建消息
				return session.createObjectMessage(member);
			}
		});
	}

}
