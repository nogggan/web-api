package com.gxf.webapi.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gxf.webapi.aspect.RoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	@Bean(destroyMethod="close",value=RoutingDataSource.MASTER)
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource masterDataSource(){
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean(destroyMethod="close",value=RoutingDataSource.SLAVE)
	@ConfigurationProperties(prefix="spring.datasource-slave")
	public DataSource slaveDataSource(){
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}	
}
