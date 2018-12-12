package com.dynamicproxy;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;

public class InterfaceProxy<T> implements InvocationHandler, Serializable {

	private static final long serialVersionUID = -6424540398559729838L;
	private final SqlSession sqlSession;
	private final Class<T> demoInterface;
	private final Map<Method, MapperMethod> methodCache;

	public InterfaceProxy(SqlSession sqlSession, Class<T> demoInterface, Map<Method, MapperMethod> methodCache) {
		this.sqlSession = sqlSession;
		this.demoInterface = demoInterface;
		this.methodCache = methodCache;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			if (Object.class.equals(method.getDeclaringClass())) {
				return method.invoke(this, args);
			} else if (isDefaultMethod(method)) {
				return invokeDefaultMethod(proxy, method, args);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		final MapperMethod mapperMethod = cachedMapperMethod(method);
	    return mapperMethod.execute(sqlSession, args);
	}

	private MapperMethod cachedMapperMethod(Method method) {
		MapperMethod mapperMethod = methodCache.get(method);
	    if (mapperMethod == null) {
	    	try {
	    		mapperMethod = new MapperMethod(demoInterface, method, sqlSession.getConfiguration());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	methodCache.put(method, mapperMethod);
	    }
	    return mapperMethod;
	}

	private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
		/*
		 * 拿到MethodHandles.Lookup里的构造方法：
		 * private Lookup(Class<?> lookupClass, int allowedModes)
		 * */
		final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
		if (!constructor.isAccessible()) {
			constructor.setAccessible(true);
		}
		final Class<?> declaringClass = method.getDeclaringClass();
		return constructor
				.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
				.unreflectSpecial(method, declaringClass)
				.bindTo(proxy)
				.invokeWithArguments(args);
	}

	/**判断是否是静态方法
	 *
	 * getModifiers()
	 *  PUBLIC: 1     （二进制  0000 0001）
		PRIVATE: 2    （二进制  0000 0010）
		PROTECTED: 4 （二进制  0000 0100）
		STATIC: 8 （二进制  0000 1000）
		FINAL: 16 （二进制  0001 0000）
		SYNCHRONIZED: 32  （二进制  0010 0000）
		VOLATILE: 64  （二进制  0100 0000）
		TRANSIENT: 128  （二进制  1000 0000）
		NATIVE: 256   （二进制 0001  0000 0000）
		INTERFACE: 512  （二进制  0010 0000 0000）
		ABSTRACT: 1024  （二进制  0100 0000 0000）
		STRICT: 2048  （二进制 1000 0000 0000）

		如：
		public static String s = "123";
		获取到的值为1+8 = 9;
		1001
		如：
		protected static volatile String s = "12323";
		获取到的值为：4+8+64 = 76
		1001100

		想获取一个方法或变量是否拥有某个修饰符可用下面的方式：
		如想知道变量是否拥有static修饰：
		int m = field.getModifiers();
		int i = 8 & m;
		如果i==0表示非static，否则就是有static修饰
		或者i==8(本身)表示static，反之为否。
	 * @param method
	 * @return
	 */
	private boolean isDefaultMethod(Method method) {
		//是否是public方法
		Modifier.isPublic(method.getModifiers());
		//是否是static方法
		Modifier.isStatic(method.getModifiers());
		//是否是abstract方法
		Modifier.isAbstract(method.getModifiers());
		return ((method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC) && method.getDeclaringClass().isInterface();
	}

}
