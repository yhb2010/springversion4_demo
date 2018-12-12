package com.config;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//用于在基于Java类定义Bean配置中开启MVC支持，和XML中的<mvc:annotation-driven>功能一样
@EnableWebMvc
//proxy-target-class="true"无需接口代理
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//<context:component-scan base-package="com.version4" />
@ComponentScan("com.version4")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver viewResolver(){
		//配置jsp视图解析器
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultservlethandlerconfigurer){
		//处理静态资源
		defaultservlethandlerconfigurer.enable();
	}

	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:properties/flex");
	    messageSource.setCacheSeconds(10);
	    return messageSource;
	}

	@Bean
	//使用servlet3.0解析multipart请求
	public MultipartResolver multipartResolver() throws IOException{
		//这里不能设置一些参数，可以在customizeRegistration里设置
		return new StandardServletMultipartResolver();
	}

}
