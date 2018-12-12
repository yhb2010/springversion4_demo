package com.databasepool;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ConnectionParam implements Serializable {

	private String driver;              //数据库驱动程序
    private String url;                 //数据连接的URL
    private String user;                    //数据库用户名
    private String password;                //数据库密码
    private int minConnection = 0;      //初始化连接数
    private int maxConnection = 50;     //最大连接数
    private long timeoutValue = 600000;//连接的最大空闲时间
    private long waitTime = 30000;      //取连接的时候如果没有可用连接最大的等待时间

	public ConnectionParam(String driver, String url, String user, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}

}
