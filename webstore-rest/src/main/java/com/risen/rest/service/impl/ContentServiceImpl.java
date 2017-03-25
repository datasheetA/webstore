package com.risen.rest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.risen.common.dao.RedisDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.risen.common.utils.JsonUtil;
import com.risen.mapper.TbContentMapper;
import com.risen.pojo.TbContent;
import com.risen.pojo.TbContentExample;
import com.risen.pojo.TbContentExample.Criteria;
import com.risen.rest.service.ContentService;

/**
 * 首页展示内容服务
 * @author sen
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Resource
	private TbContentMapper contentMapper;
	
	@Resource
	private RedisDao redisDao;
	
	@Value("${INDEX_CONTENT_REDIS_HASHKEY}")
	private String hkey;
	
	/**
	 * 获取展示内容列表
	 */
	@Override
	public List<TbContent> getContentList(long categoryId) {
		/*
		 * 从缓存中取内容
		 */
		String cacheStr=redisDao.hget(hkey, categoryId+"");
		if(!StringUtils.isBlank(cacheStr)){
			//将字符串转化成list
			List<TbContent> list = JsonUtil.jsonToList(cacheStr, TbContent.class);
			return list;
		}

		//设置查询条件
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		/*
		 * 把内容添加到缓存中
		 */
		String cacheStr2=JsonUtil.objectToJson(list);
		redisDao.hset(hkey, categoryId+"", cacheStr2);

		return list;
	}

}
