package com.version4.chapter19mail.service;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.config.MailConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MailConfig.class})
public class MailTest {

	@Autowired
	private EmailService emailService;

	@Test
	public void sendSimpleEmail(){
		emailService.sendSimpleEmail("youtong@163.com", "测试");
	}

	@Test
	public void sendEmailWithAttachment() throws MessagingException{
		emailService.sendEmailWithAttachment("youtong82@163.com", "测试");
	}

	@Test
	public void sendRichEmail() throws MessagingException{
		emailService.sendRichEmail("youtong82@163.com", "测试");
	}

	@Test
	public void sendTemplateEmail() throws MessagingException{
		emailService.sendTemplateEmail("youtong82@163.com");
	}

}
