package com.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**在servlet3.0环境中，容器会在类路径里查找实现了javax.servlet.ServletContainerInitializer接口的类，
 * 如果能够发现，就会用它来配置servlet容器
 * spring提供了这个接口的实现，名为SpringServletContainerIniltializer，这个类反过来又会查找实现了WebApplicationInitializer
 * 的类并将配置的任务交给它们来完成，spring3.2引入了一个便利的WebApplicationInitializer的基础实现，
 * 也就是AbstractAnnotationConfigDispatcherServletInitializer，我们的DemoWebAppInitializer扩展了AbstractAnnotationConfigDispatcherServletInitializer，
 * 因此当部署到servlet3.0容器的时候，容器会自动发现它，并用它来配置servlet上下文。
 * @author dell
 * 零配置http://hanqunfeng.iteye.com/blog/2114975
 */
public class DemoWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
	 * 返回的类将会用来定义ContextLoaderListener应用上下文中的bean
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{RootConfig.class, MongoConfig.class, RedisConfig.class, MailConfig.class};
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
	 * 返回的类将会用来定义DispatcherServlet应用上下文中的bean
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{WebConfig.class, CachingConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		//将DispatcherServlet映射到"/"
		return new String[]{"/"};
	}

	@Override
	//配置上传附件multipart解析器
	//可以使用CommonsMultipartResolver，它是基于fileupload的
	//servlet3.0之后可以使用StandardServletMultipartResolver，推荐这个
	protected void customizeRegistration(Dynamic dynamic){
		//arg0临时目录
		//arg1单个文件大小
		//arg2全部文件大小
		//arg3上传时，如果文件大小大于arg3时，将会写入临时路径，为0，则所有上传文件都会写入磁盘
		dynamic.setMultipartConfig(new MultipartConfigElement("e:/zsl/uploads", 2097152, 4194304, 0));
	}

	/*
     * 注册过滤器，映射路径与DispatcherServlet一致，路径不一致的过滤器需要注册到另外的WebApplicationInitializer中
     */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] {characterEncodingFilter};
	}

}
