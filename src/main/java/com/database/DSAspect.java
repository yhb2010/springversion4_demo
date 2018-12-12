package com.database;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Service("dsAspectBean")
@Aspect
public class DSAspect {

	/**
     * Pointcut
     * 定义Pointcut，Pointcut的名称为changeDb()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
	@Pointcut("execution(* *..*Mapper.*(..))")
	public void changeDb() {
	}

	@Before("changeDb()")
	public void doBefore(JoinPoint jp) {
		DbContextHolder.setDbType("xxxReader");
	}

}
