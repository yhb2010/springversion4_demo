package com.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.remoting.caucho.BurlapProxyFactoryBean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.database.DynamicDataSource;
import com.version4.chapter15.service.BurlapRemoteUserService;
import com.version4.chapter15.service.HessianRemoteUserService;
import com.version4.chapter15.service.HttpInvokerRemoteUserService;
import com.version4.chapter15.service.JaxWsUserService;
import com.version4.chapter15.service.RemoteUserService;

@Configuration
//只是用了@ComponentScan，这样就可以在项目中用非web的组件来充实完善RootConfig
@ComponentScan(basePackages = {"com.version4"}, excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
@ImportResource({"classpath:config/jms/jms.xml", "classpath:config/jms/jms-client.xml", "classpath:config/jms/jms-pub.xml"})
public class RootConfig {

	@Bean
	public JndiObjectFactoryBean accJiaowuReader(){
		JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
		factory.setJndiName("java:comp/env/jdbc/xxx_reader");
		//factory.setProxyInterface(javax.sql.DataSource.class);
		return factory;
	}

	@Bean
	public JndiObjectFactoryBean accJiaowuWriter(){
		JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
		factory.setJndiName("java:comp/env/jdbc/xxx_writer");
		//factory.setProxyInterface(javax.sql.DataSource.class);
		return factory;
	}

	@Bean
	//动态切换数据源
	public DynamicDataSource dataSource2() throws Exception{
		DynamicDataSource ds = new DynamicDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("xxxReader", accJiaowuReader().getObject());
		targetDataSources.put("xxxWriter", accJiaowuWriter().getObject());
		ds.setTargetDataSources(targetDataSources);
		ds.setDefaultTargetDataSource(accJiaowuReader().getObject());
		return ds;
	}

	@Bean
	//配置jdbc数据源
	public JndiObjectFactoryBean dataSource(){
		JndiObjectFactoryBean JndiObjectFB = new JndiObjectFactoryBean();
		JndiObjectFB.setJndiName("jdbc/spittrDS");
		//可以自动加上java:comp/env/前缀
		JndiObjectFB.setResourceRef(true);
		JndiObjectFB.setProxyInterface(javax.sql.DataSource.class);
		return JndiObjectFB;
	}

	@Bean
	//配置jdbcTemplate
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}

	@Bean
	//配置hibernate数据源
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource);
		//支持注解的方式(hibernate4.LocalSessionFactoryBean)
		sfb.setPackagesToScan(new String[]{"com.version4.chapter11.domain"});
		Properties props = new Properties();
		props.setProperty("dialect", "org.hibernate.dialect.SQLServerDialect");
		sfb.setHibernateProperties(props);
		return sfb;
	}

	@Bean
	//jpa持久化
	//JpaVendorAdapter指明是使用哪个厂商的jpa实现
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan(new String[]{"com.version4.chapter11.domain"});
		return emfb;
	}

	@Bean
	//用hibernate的jpa实现
	public JpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.SQLServerDialect");
		return adapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	//rmi客户端注册
	@Bean
	public RmiProxyFactoryBean remoteUserService(){
		RmiProxyFactoryBean rmiProxy = new RmiProxyFactoryBean();
		rmiProxy.setServiceUrl("rmi://localhost:1100/RemoteUserService");
		rmiProxy.setServiceInterface(RemoteUserService.class);
		return rmiProxy;
	}

	//hessian客户端注册
	@Bean
	public HessianProxyFactoryBean hessianRemoteUserService(){
		HessianProxyFactoryBean rmiProxy = new HessianProxyFactoryBean();
		rmiProxy.setServiceUrl("http://localhost:8081/service/hessianRemoteUserService.service");
		rmiProxy.setServiceInterface(HessianRemoteUserService.class);
		return rmiProxy;
	}

	//burlap客户端注册
	@Bean
	public BurlapProxyFactoryBean burlapRemoteUserService(){
		BurlapProxyFactoryBean rmiProxy = new BurlapProxyFactoryBean();
		rmiProxy.setServiceUrl("http://localhost:8081/service/burlapRemoteUserService.service");
		rmiProxy.setServiceInterface(BurlapRemoteUserService.class);
		return rmiProxy;
	}

	//httpInvoker客户端注册
	@Bean
	public HttpInvokerProxyFactoryBean httpInvokerRemoteUserService() {
		HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
		proxy.setServiceUrl("http://localhost:8081/service/httpInvokerRemoteUserService.service");
		proxy.setServiceInterface(HttpInvokerRemoteUserService.class);
		return proxy;
	}

	//jaxws客户端注册
	@Bean
	public JaxWsPortProxyFactoryBean spitterService() throws MalformedURLException {
		JaxWsPortProxyFactoryBean proxy = new JaxWsPortProxyFactoryBean();
		proxy.setWsdlDocumentUrl(new URL("http://localhost:8888/services/JaxWsService?wsdl"));
		//下面三个参数是由http://localhost:8888/services/JaxWsService?wsdl地址找到的：
		//<service name="JaxWsService">
		proxy.setServiceName("JaxWsService");
		//<port name="JaxWsServiceEndpointPort"
		proxy.setPortName("JaxWsServiceEndpointPort");
		proxy.setServiceInterface(JaxWsUserService.class);
		//targetNamespace="http://service.chapter15.version4.com/"
		proxy.setNamespaceUri("http://service.chapter15.version4.com/");
		return proxy;
	}

}
