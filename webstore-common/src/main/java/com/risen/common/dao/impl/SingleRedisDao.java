package com.risen.common.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.risen.common.dao.RedisDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SingleRedisDao implements RedisDao {
	
	@Resource
	private JedisPool jedisPool;
	
	@Override
	public String get(String key) {
		Jedis jedis=jedisPool.getResource();
		String value=jedis.get(key);
		jedis.close();
		return value;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis=jedisPool.getResource();
		String str=jedis.set(key, value);
		jedis.close();
		return str;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis=jedisPool.getResource();
		String value=jedis.hget(hkey, key);
		jedis.close();
		return value;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis=jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis=jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis=jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis=jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public long del(String key) {
		Jedis jedis=jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}


	@Override
	public long hdel(String hkey, String... fields) {
		Jedis jedis=jedisPool.getResource();
		Long result = jedis.hdel(hkey, fields);
		jedis.close();
		return result;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		Jedis jedis=jedisPool.getResource();
		Map<String,String> result = jedis.hgetAll(key);
		jedis.close();
		return result;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		Jedis jedis=jedisPool.getResource();
		List<String> list = jedis.hmget(key, fields);
		jedis.close();
		return list;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis=jedisPool.getResource();
		String result = jedis.hmset(key, hash);
		jedis.close();
		return result;
	}
	

}
