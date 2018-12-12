package com.dynamicproxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;

public class InterfaceProxyFactory<T> {

	private final Class<T> demoInterface;
	private final Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<Method, MapperMethod>();

	public InterfaceProxyFactory(Class<T> demoInterface) {
		this.demoInterface = demoInterface;
	}

	public Class<T> getDemoInterface() {
		return demoInterface;
	}

	@SuppressWarnings("unchecked")
	protected T newInstance(InterfaceProxy<T> interfaceProxy) {
		return (T) Proxy.newProxyInstance(demoInterface.getClassLoader(), new Class[] { demoInterface }, interfaceProxy);
	}

	public T newInstance(SqlSession sqlSession) {
	    final InterfaceProxy<T> interfaceProxy = new InterfaceProxy<T>(sqlSession, demoInterface, methodCache);
	    return newInstance(interfaceProxy);
	}

}
