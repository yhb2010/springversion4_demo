package com.version4.chapter19mail.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	public void sendSimpleEmail(String to, String content) {
		SimpleMailMessage message = new SimpleMailMessage();// 构造信息
		message.setFrom("test@xxx.com");
		message.setTo(to);
		message.setSubject("一封测试邮件");

		message.setText(content);

		mailSender.send(message);
	}

	public void sendEmailWithAttachment(String to, String content)
			throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		// 使用spring提供的MimeMessageHelper，true表示该信息是multipart类型的
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("test@xxx.com");
		helper.setTo(to);
		helper.setSubject("一封测试邮件2");
		helper.setText(content);

		// 记载位于应用类路径下的图片文件
		ClassPathResource couponImage = new ClassPathResource(
				"/com/version4/chapter19mail/service/a.jpg");
		helper.addAttachment("说明.jpg", couponImage);

		mailSender.send(message);
	}

	public void sendRichEmail(String to, String content)
			throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		// 使用spring提供的MimeMessageHelper，true表示该信息是multipart类型的
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom("test@xxx.com");
		helper.setTo(to);
		helper.setSubject("一封测试邮件3");
		// true表示传递进来的是html
		helper.setText("<html><body><img src='cid:spitterLogo'>" + "<h4>张苏磊 说"
				+ content + "</h4>" + "<i>这是一个测试邮件</i>" + "</body></html>",
				true);

		// 记载位于应用类路径下的图片文件
		ClassPathResource couponImage = new ClassPathResource(
				"/com/version4/chapter19mail/service/a.jpg");
		helper.addInline("spitterLogo", couponImage);

		mailSender.send(message);
	}

	public void sendTemplateEmail(String to) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		// 使用spring提供的MimeMessageHelper，true表示该信息是multipart类型的
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom("test@xxx.com");
		helper.setTo(to);
		helper.setSubject("一封测试邮件4");
		String htmlText = getMailText();// 使用模板生成html邮件内容
		helper.setText(htmlText, true);

		// 记载位于应用类路径下的图片文件
		ClassPathResource couponImage = new ClassPathResource(
				"/com/version4/chapter19mail/service/a.jpg");
		helper.addInline("spitterLogo", couponImage);

		mailSender.send(message);
	}

	/**
	 * 生成html模板字符串
	 *
	 * @param root
	 *            存储动态数据的map
	 * @return
	 */
	private String getMailText() {
		String htmlText = "";
		try {
			// 通过指定模板名获取FreeMarker模板实例
			Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(
					"registerUser.ftl");

			// FreeMarker通过Map传递动态数据
			Map map = new HashMap();
			map.put("creatMan", "张苏磊");
			map.put("reportTypeName", "学习报告");
			map.put("unusualTypeName", "bug");
			map.put("reportLevel", "是");
			map.put("creatDate", "2015年10月1日");
			map.put("reportExplain", "服务器宕机");

			// 解析模板并替换动态数据，最终username将替换模板文件中的${username}标签。
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,
					map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return htmlText;
	}

}
