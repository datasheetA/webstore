package com.risen.rest.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.risen.common.utils.JsonUtil;
import com.risen.common.utils.Result;
import com.risen.mapper.TbItemMapper;
import com.risen.pojo.TbItem;
import com.risen.rest.dao.RedisDao;
import com.risen.rest.service.ItemService;

/**
 * 商品详情页面展示service层
 * @author sen
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Resource
	private TbItemMapper itemMapper;
	
	@Resource
	private RedisDao redisDao;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	
	/**
	 * 取商品基本信息
	 */
	@Override
	public Result getItemBaseInfo(long itemId) {
		
		/*
		 * 缓存逻辑
		 */
		try{
			//从缓存中取数据
			String json = redisDao.get(REDIS_ITEM_KEY+":" + itemId +":base");
			//判断是否有值
			if(!StringUtils.isBlank(json)){
				//将json字符串转成java对象
				TbItem item = JsonUtil.jsonToPojo(json, TbItem.class);
				return Result.ok(item);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//根据商品id查询商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		/*
		 * 写入缓存
		 */
		try {
			//将item转成json字符串
			String json = JsonUtil.objectToJson(item);
			//写入redis
			redisDao.set(REDIS_ITEM_KEY+":" + itemId +":base", json);
			//设置过期时间
			redisDao.expire(REDIS_ITEM_KEY+":" + itemId +":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Result.ok(item);
		
	}

}
