package com.databasepool;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class DataSourceImpl implements DataSource {

	private static List<_Connection> pool = new LinkedList<>();
	private AtomicInteger num = new AtomicInteger(1);
	private ConnectionParam param;
	private AtomicInteger connectionCount = new AtomicInteger(0);

	public DataSourceImpl(ConnectionParam param) {
		super();
		this.param = param;
	}

	public void initConnection() {
		try {
			Class.forName(param.getDriver());
			for(int i=0; i<param.getMinConnection(); i++){
				Connection conn = DriverManager.getConnection(param.getUrl(), param.getUser(), param.getPassword());
		        pool.add(new _Connection(conn, false, num.getAndIncrement()));
		        connectionCount.set(pool.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		//首先从连接池中找出空闲的对象
	    Connection conn = getFreeConnection(0);
	    while(conn == null){
	        //判断是否超过最大连接数,如果超过最大连接数，则等待一定时间查看是否有空闲连接,否则抛出异常告诉用户无可用连接
	    	int count = getConnectionCount();
	        if(count >= param.getMaxConnection())
	            conn = getFreeConnection(param.getWaitTime());
	        else{//没有超过连接数，重新获取一个数据库的连接
	        	if(connectionCount.compareAndSet(count, count + 1)){
	        		try{
			            Connection conn2 = DriverManager.getConnection(param.getUrl(), param.getUser(), param.getPassword());
			            //代理将要返回的连接对象
			            _Connection _conn = new _Connection(conn2, true, num.getAndIncrement());
		            	System.out.println("[" + Thread.currentThread().getName() + "]新建了连接");
		            	conn = _conn.getConnection();
		            	pool.add(_conn);
	        		}catch(Exception e){
	        			connectionCount.decrementAndGet();
	        		}
	        	}else{
	        		System.err.println("[" + Thread.currentThread().getName() + "]自旋");
	        	}
	        }
	    }
	    return conn;
	}

	private int getConnectionCount() {
		return connectionCount.get();
	}

	/**
	 * 从连接池中取一个空闲的连接
	 * @param nTimeout  如果该参数值为0则没有连接时只是返回一个null
	 * 否则的话等待nTimeout毫秒看是否还有空闲连接，如果没有抛出异常
	 * @return Connection
	 * @throws SQLException
	 */
	protected Connection getFreeConnection(long nTimeout) throws SQLException {
	    Connection conn = null;
	    Iterator<_Connection> iter = pool.iterator();
	    while(iter.hasNext()){
	        _Connection _conn = iter.next();
        	/**有同步问题，需要加同步块
        	 * if(!_conn.isInUse()){
        		conn = _conn.getConnection();
        		_conn.setInUse(true);
        		break;
        	}
        	*/
	        if(_conn.setInUse(false, true)){
	        	conn = _conn.getConnection();
	        	break;
	        }
	    }
	    if(conn == null && nTimeout > 0){
	        //等待nTimeout毫秒以便看是否有空闲连接
	        try{
	            Thread.sleep(nTimeout);
	        }catch(Exception e){}
	        conn = getFreeConnection(0);
	        if(conn == null)
	            throw new SQLException("没有可用的数据库连接");
	    }
	    return conn;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void close() {
		// TODO Auto-generated method stub

	}

}
