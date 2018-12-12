package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//会扫描扩展自JpaRepository接口的所有接口，自动生成这个接口的实现
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = "com.version4.chapter11.dao")
public class JpaConfig {

}
