package com.gxf.webapi.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.stereotype.Component;

import com.gxf.webapi.util.JsonUtils;
import com.gxf.webapi.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
@Component
@Slf4j
public class RedisService {
	
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 该方法往Redis里面存储一个值
	 * @param key 为了保证当业务扩展时，键不会重复
	 * @param name 正常的键的名字
	 * @param value 需要存储的值
	 */
	public void set(PrefixKey key,String name,Object value){
		if(StringUtils.isEmpty(name)){
			throw new NullPointerException("RedisService : set() 参数中的 name: 为空");
		}
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String realKey = key.prefix()+name;
			String realValue = JsonUtils.toString(value);
			int expireSecond = key.expire();
			if(expireSecond<=0){
				jedis.set(realKey, realValue);
			}else{
				jedis.setex(realKey, expireSecond, realValue);
			}
			log.debug("set() : 键:{},值:{}",realKey,realValue);
		}finally{
			close(jedis);
		}
	}
	
	/**
	 * 该方法从Redis中获取一个JavaBean 
	 * @param key 
	 * @param name 
	 * @param clazz 期望得到的JavaBean的类型 （不能为空）
	 * @return 期望的JavaBean
	 */
	public <T> T get(PrefixKey key,String name,Class<T> clazz){
		if(StringUtils.isEmpty(name) || clazz == null)
			return null;
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String realKey = key.prefix()+name;
			String realValue = jedis.get(realKey);
			T result = JsonUtils.toObject(realValue, clazz);
			log.debug("get() : 键:{},值:{},返回值:{}",realKey,realValue,result);
			return result;
		}finally{
			close(jedis);
		}
	}
	
	/**
	 * 把指定的键自增
	 * @param key
	 * @param name
	 * @return
	 */
	public Long incr(PrefixKey key,String name){
		if(StringUtils.isEmpty(name))
			throw new NullPointerException("RedisService : incr() 参数中的 name : 为空 ");
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String realKey = key.prefix()+name;
			log.debug("incr() : 键:{}",realKey,realKey);
			return jedis.incr(realKey);
		}finally{
			close(jedis);
		}
	}
	
	/**
	 * 把指定的键的值自减
	 * @param key
	 * @param name
	 * @return
	 */
	public Long decr(PrefixKey key,String name){
		if(StringUtils.isEmpty(name))
			throw new NullPointerException("RedisService : decr() 参数中的 name : 为空 ");
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String realKey = key.prefix()+name;
			log.debug("decr() : 键:{}",realKey);
			return jedis.decr(realKey);
		}finally{
			close(jedis);
		}
	}
	
	public void del(PrefixKey key,String name){
		if(StringUtils.isEmpty(name))
			return;
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String realKey = key.prefix()+name;
			jedis.del(realKey);
		}finally{
			close(jedis);
		}
	}
	
	public void close(Jedis jedis){
		if(jedis != null){
			jedis.close();
		}
	}

}
