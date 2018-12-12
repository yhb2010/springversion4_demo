package com.databasepool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 数据连接的自封装，屏蔽了close方法
 *
 * @author Liudong
 */
public class _Connection implements InvocationHandler {

	private final static String CLOSE_METHOD_NAME = "close";
	private Connection conn = null;
	// 数据库的忙状态
	private AtomicBoolean inUse = new AtomicBoolean(false);
	private int num = 0;;
	// 用户最后一次访问该连接方法的时间
	private long lastAccessTime = System.currentTimeMillis();

	_Connection(Connection conn, boolean inUse, int num) {
		this.conn = conn;
		this.inUse.set(inUse);
		this.num = num;
	}

	/**
	 * Returns the conn.
	 *
	 * @return Connection
	 */
	public Connection getConnection() {
		// 返回数据库连接conn的接管类，以便截住close方法
		Connection conn2 = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(), this);
		System.out.println("[" + Thread.currentThread().getName() + "]拿到了" + num + "号连接");
		return conn2;
	}

	/**
	 * 该方法真正的关闭了数据库的连接
	 *
	 * @throws SQLException
	 */
	void close() throws SQLException {
		// 由于类属性conn是没有被接管的连接，因此一旦调用close方法后就直接关闭连接
		conn.close();
	}

	/**
	 * Returns the inUse.
	 *
	 * @return boolean
	 */
	public boolean setInUse(boolean expect, boolean update) {
		if(!inUse.compareAndSet(expect, update)){
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object)
	 */
	public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
		Object obj = null;
		// 判断是否调用了close的方法，如果调用close方法则把连接置为无用状态
		if (CLOSE_METHOD_NAME.equals(m.getName()))
			setInUse(false);
		else
			obj = m.invoke(conn, args);
		// 设置最后一次访问时间，以便及时清除超时的连接
		lastAccessTime = System.currentTimeMillis();
		return obj;
	}

	/**
	 * Returns the lastAccessTime.
	 *
	 * @return long
	 */
	public long getLastAccessTime() {
		return lastAccessTime;
	}

	/**
	 * Sets the inUse.
	 *
	 * @param inUse
	 *            The inUse to set
	 */
	public void setInUse(boolean inUse) {
		this.inUse.set(inUse);
	}

	/**
	 * Returns the inUse.
	 *
	 * @return boolean
	 */
	public boolean isInUse() {
		return inUse.get();
	}

}