package com.version4.chapter17jms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Service;
import com.version4.chapter10.domain.Member;

@Service
public class AlertServiceImpl2 implements AlertService {

	@Autowired
	//JmsOperations：JmsTemplate实现此接口
	private JmsOperations jmsTemplate;

	public void sendSpittleAlert(final Member member) {
		System.out.println("---------------生产者发了一个消息：" + member);

		//convertAndSend方法内置了转换器，所以使用起来更加简单
		//spring为通用的转换任务提供了多个消息转换器P474，如MappingJackson2MessageConverter，MarshallingMessageConverter，SimpleMessageConverter
		//默认情况下，使用SimpleMessageConverter，但也可以声明转换器，然后注入jmsTemplate，如
		//<bean id="messageConverter" class="org.springframework.jms.support.converter.MappingJacksonMessageConverter" />
		//<bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate" c:_-ref="connectionFactory" p:defaultDestinationName="spittle.alert.queue" p:messageConverter-ref="messageConverter" />
		jmsTemplate.convertAndSend(member);
	}

}
