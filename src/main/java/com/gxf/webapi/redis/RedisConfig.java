package com.gxf.webapi.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
	
	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
	public JedisPool jedisPool(){
		JedisPoolConfig config = new JedisPoolConfig();
		JedisPool jedisPool = null;
		config.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
		config.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
		config.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
		config.setMaxWaitMillis(5000);
		jedisPool = new JedisPool(config,redisProperties.getHost(),
				redisProperties.getPort(),3000,redisProperties.getPassword(),0);
		return jedisPool;
	}
	
}
