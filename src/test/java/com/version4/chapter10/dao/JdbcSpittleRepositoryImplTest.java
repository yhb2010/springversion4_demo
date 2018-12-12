package com.version4.chapter10.dao;

import javax.sql.DataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.config.RootConfig;
import com.version4.chapter10.domain.Member;
import com.version4.mvc.chapter56.exception.DuplicateSpittleException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class JdbcSpittleRepositoryImplTest {

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
	private MemberRepository memberRepository;

	@Test
	public void save() throws DuplicateSpittleException {
		Member member = new Member();
		member.setUserName("9999");
		memberRepository.save(member, "99");
	}

	@Test
	public void findOne(){
		Member member = memberRepository.findByUserName("99");
		System.out.println(member);
	}

}
