package com.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
@ComponentScan("com.version4.chapter19mail")
@PropertySource("classpath:/config/mail.properties")
public class MailConfig {

	@Bean
	public JavaMailSender mailSender(Environment env){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getProperty("mail.host"));
		mailSender.setUsername(env.getProperty("mail.username"));
		mailSender.setPassword(env.getProperty("mail.password"));
		return mailSender;
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer(){
		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		config.setTemplateLoaderPath("classpath:config");
		Properties settings = new Properties();
		settings.setProperty("template_update_delay", "1800");
		settings.setProperty("default_encoding", "UTF-8");
		settings.setProperty("locale", "zh_CN");
		config.setFreemarkerSettings(settings);
		return config;
	}

}
