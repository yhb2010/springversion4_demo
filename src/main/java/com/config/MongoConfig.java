package com.config;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

//spring data mongodb配置
@Configuration
//从外部配置文件注入值
@PropertySource("classpath:/config/mongodb.properties")
//启用spring data mongodb
@EnableMongoRepositories("com.version4.chapter12")
public class MongoConfig extends AbstractMongoConfiguration {

	private static final int DEFAULTPORT = 27017;

	@Autowired
	Environment env;

	//指定数据库名
	@Override
	protected String getDatabaseName() {
		return env.getProperty("mongodb.db");
	}

	//创建mongodb客户端
	@Override
	public Mongo mongo() throws Exception {
		//开启mongodb认证
		MongoCredential credential = MongoCredential.createMongoCRCredential(env.getProperty("mongodb.username"), env.getProperty("mongodb.db"), env.getProperty("mongodb.pwd").toCharArray());
		//创建mongoClient
		return new MongoClient(getSeeds(), Arrays.asList(credential));
	}

	private List<ServerAddress> getSeeds() throws NumberFormatException, UnknownHostException{

		List<ServerAddress> seeds = new ArrayList<ServerAddress>();

		String[] hostPorts = env.getProperty("mongodb.hosts").split(",");
		String[] hp = null;
		String host = null;
		int port = 0;
		for (String hostPort : hostPorts) {
			hp = hostPort.split(":");
			if(hp == null || hp.length <= 0){
				host = "127.0.0.1";
				port = DEFAULTPORT;
			}else {
				if(hp.length == 1){
					host = hp[0];
					port = DEFAULTPORT;
				}else{
					host = hp[0];
					port = Integer.parseInt(hp[1]);
				}
			}
			seeds.add(new ServerAddress(host, port));
		}

		return seeds;
	}

}
