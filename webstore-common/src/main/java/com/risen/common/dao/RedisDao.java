package com.risen.common.dao;

import java.util.List;
import java.util.Map;

public interface RedisDao {
	
	
	String get(String key);
	
	String set(String key,String value);
	
	String hget(String hkey,String key);
	
	long hset(String hkey,String key,String value);
	
	long incr(String key);
	
	long expire(String key,int second);
	
	long ttl(String key);
	
	long del(String key);
	
	long hdel(String hkey,String...fields);
	
	Map<String,String> hgetAll(String key);
	
	List<String> hmget(String key,String...fields);
	
	String hmset(String key,Map<String,String> hash);
}
