package com.dynamicproxy;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class InterfaceRegistry {

	private final Map<Class<?>, InterfaceProxyFactory<?>> knownMappers = new HashMap<Class<?>, InterfaceProxyFactory<?>>();

	@SuppressWarnings("unchecked")
	public <T> T getMapper(Class<T> type, SqlSession sqlSession) throws Exception {
		final InterfaceProxyFactory<T> interfaceProxyFactory = (InterfaceProxyFactory<T>) knownMappers.get(type);
		if (interfaceProxyFactory == null) {
			throw new Exception("Type " + type + " is not known to the InterfaceRegistry.");
		}
		try {
			return interfaceProxyFactory.newInstance(sqlSession);
		} catch (Exception e) {
			throw new Exception("Error getting interface instance. Cause: " + e, e);
		}
	}

	public <T> boolean hasMapper(Class<T> type) {
		return knownMappers.containsKey(type);
	}

	public <T> void addMapper(Class<T> type) throws Exception {
		if (type.isInterface()) {
			if (hasMapper(type)) {
				throw new Exception("Type " + type + " is already known to the InterfaceRegistry.");
			}
			knownMappers.put(type, new InterfaceProxyFactory<T>(type));
		}
	}

}
