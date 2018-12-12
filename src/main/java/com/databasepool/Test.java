package com.databasepool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;

public class Test {

	public static void main(String[] args) throws Exception {
		String name = "pool";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://127.0.0.250:port;DatabaseName=dbName";
		ConnectionParam param = new ConnectionParam(driver, url, "sa", "123");
		param.setMinConnection(1);
		param.setMaxConnection(5);
		param.setWaitTime(500);
		ConnectionFactory.bind(name, param);
		System.out.println("bind datasource ok.");
		//以上代码是用来登记一个连接池对象，该操作可以在程序初始化只做一次即可
		//以下开始就是使用者真正需要写的代码
		DataSource ds = ConnectionFactory.lookup(name);
		try{
		    for(int i=0; i<30; i++){
		    	new Thread(() -> {
		    		Connection conn = null;
			        try{
			        	conn = ds.getConnection();
			            testSQL(conn, "select top 1 * from qz_question order by questionID desc");
			        }catch (Exception e) {
			        	System.err.println("[" + Thread.currentThread().getName() + "]没有获取到连接");
					}finally{
			            try{
			            	if(conn != null)
			            		conn.close();
			            }catch(Exception e){}
			        }
		    	}).start();
		    }
		}catch(Exception e){
		    e.printStackTrace();
		}finally{
		    //ConnectionFactory.unbind(name);
		    //System.out.println("unbind datasource ok.");
		    //System.exit(0);
		}
	}

	private static void testSQL(Connection conn, String sql) throws Exception {
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Integer questionID = rs.getInt("questionID");
			System.out.println("[" + Thread.currentThread().getName() + "]" + questionID);
		}
	}

}
