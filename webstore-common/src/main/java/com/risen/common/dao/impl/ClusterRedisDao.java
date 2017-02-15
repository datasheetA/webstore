package com.risen.common.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.risen.common.dao.RedisDao;

import redis.clients.jedis.JedisCluster;

public class ClusterRedisDao implements RedisDao {
	@Resource
	private JedisCluster jedisCluster;
	
	@Override
	public String get(String key) {
		
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String hget(String hkey, String key) {
		return jedisCluster.hget(hkey, key);
	}

	@Override
	public long hset(String hkey, String key, String value) {
		return jedisCluster.hset(hkey, key, value);
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public long expire(String key, int second) {
		return jedisCluster.expire(key, second);
	}

	@Override
	public long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public long hdel(String hkey, String...fileds) {
		return jedisCluster.hdel(hkey, fileds);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return jedisCluster.hgetAll(key);
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return jedisCluster.hmget(key, fields);
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return jedisCluster.hmset(key, hash);
	}
	
}
