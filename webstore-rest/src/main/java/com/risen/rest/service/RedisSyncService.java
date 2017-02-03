package com.risen.rest.service;

import com.risen.common.utils.Result;


/**
 * redis缓存同步接口定义
 * @author sen
 *
 */
public interface RedisSyncService {
	
	Result syncContent(long categoryId);
}
