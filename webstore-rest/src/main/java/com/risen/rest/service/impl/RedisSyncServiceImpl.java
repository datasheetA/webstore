package com.risen.rest.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.risen.common.utils.ExceptionUtil;
import com.risen.common.utils.Result;
import com.risen.rest.dao.RedisDao;
import com.risen.rest.service.RedisSyncService;

/**
 * redis缓存同步服务层
 * @author sen
 *
 */
@Service
public class RedisSyncServiceImpl implements RedisSyncService{
	
	@Resource
	private RedisDao redisDao;
	
	@Value("${INDEX_CONTENT_REDIS_HASHKEY}")
	private String hkey;
	
	@Override
	public Result syncContent(long categoryId) {
		try {
			
		redisDao.hdel(hkey, categoryId+"");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(500, ExceptionUtil.getStackTrace(e));
		}
		return Result.ok();
	}
	

}
