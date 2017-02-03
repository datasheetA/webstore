package com.risen.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisTest {
	
	@Test
	public void testJedisCluster(){
		
		HashSet<HostAndPort> nodes=new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.1.114", 7001));
		nodes.add(new HostAndPort("192.168.1.114", 7002));
		nodes.add(new HostAndPort("192.168.1.114", 7003));
		nodes.add(new HostAndPort("192.168.1.114", 7004));
		nodes.add(new HostAndPort("192.168.1.114", 7005));
		nodes.add(new HostAndPort("192.168.1.114", 7006));
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("key1","1000");
		String string = cluster.get("key1");
		System.out.println(string);
		cluster.close();
	}
	
	@Test
	public void testJedisSpring(){
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisCluster c=(JedisCluster) context.getBean("jedisCluster");
		String string = c.get("key1");
		System.out.println(string);
	}
}
