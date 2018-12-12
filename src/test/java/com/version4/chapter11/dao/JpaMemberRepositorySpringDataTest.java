package com.version4.chapter11.dao;

import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.config.JpaConfig;
import com.config.RootConfig;
import com.version4.chapter11.domain.QzMember;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, JpaConfig.class})
public class JpaMemberRepositorySpringDataTest {

	@BeforeClass
	//在单元测试里用jndi的方式注入数据源
	public static void beforeClass() throws Exception{
		ClassPathXmlApplicationContext app =new ClassPathXmlApplicationContext("classpath:config/InitJndi.xml");
		DataSource ds =(DataSource) app.getBean("dataSource");
		SimpleNamingContextBuilder builder =new SimpleNamingContextBuilder();
		builder.bind("jdbc/spittrDS", ds);
		builder.activate();
	}

	@Autowired
	private JpaMemberRepositorySpringData jpaMemberRepositorySpringData;

	@Test
	public void findOne(){
		QzMember member = jpaMemberRepositorySpringData.findOne(471475);
		System.out.println(member);
	}

	@Test
	public void findByUserName(){
		QzMember member = jpaMemberRepositorySpringData.findByUserName("99");
		System.out.println(member);
	}

	@Test
	public void findByLikeEmail(){
		List<Object> members = jpaMemberRepositorySpringData.findByLikeEmail();
		members.forEach(m -> System.out.print(m));
	}

	@Test
	public void findByLikeUserName(){
		List<Object> members = jpaMemberRepositorySpringData.findByLikeUserName("@163.com");
		members.forEach(m -> System.out.print(m));
	}

}
