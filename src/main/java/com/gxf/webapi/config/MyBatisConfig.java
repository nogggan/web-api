package com.gxf.webapi.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gxf.webapi.aspect.RoutingDataSource;
import com.gxf.webapi.util.JsonUtils;

@Configuration
public class MyBatisConfig {
	
	@Autowired
	@Qualifier(RoutingDataSource.MASTER)
	private DataSource masterDataSource;

	@Autowired
	@Qualifier(RoutingDataSource.SLAVE)
	private DataSource slaveDataSource;
	
	@Bean(name="dynamicDataSource")
	public DataSource dynamicDataSource(){
		DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
		dynamicRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
		Map<Object,Object> datasourceMap = new HashMap<>();
		datasourceMap.put(RoutingDataSource.MASTER, masterDataSource);
		datasourceMap.put(RoutingDataSource.SLAVE, slaveDataSource);
		dynamicRoutingDataSource.setTargetDataSources(datasourceMap);
		return dynamicRoutingDataSource;
	}
	
	@Bean
	@ConfigurationProperties(prefix="mybatis")
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws SQLException{
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dynamicDataSource());
		return factoryBean;
	}
	
}
