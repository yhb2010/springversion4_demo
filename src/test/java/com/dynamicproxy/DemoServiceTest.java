package com.dynamicproxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/spring-dao.xml"})
public class DemoServiceTest {

	@Autowired
	private DemoService demoService;

	@Test
	public void addUserAndFind() throws Exception {
		demoService.addUserAndFind();
	}

}
