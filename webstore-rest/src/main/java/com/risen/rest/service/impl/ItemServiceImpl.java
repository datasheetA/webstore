package com.risen.rest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.risen.common.utils.JsonUtil;
import com.risen.common.utils.Result;
import com.risen.mapper.TbItemDescMapper;
import com.risen.mapper.TbItemMapper;
import com.risen.mapper.TbItemParamItemMapper;
import com.risen.pojo.TbItem;
import com.risen.pojo.TbItemDesc;
import com.risen.pojo.TbItemDescExample;
import com.risen.pojo.TbItemParamItem;
import com.risen.pojo.TbItemParamItemExample;
import com.risen.pojo.TbItemParamItemExample.Criteria;
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
	private TbItemDescMapper itemDescMapper;
	
	@Resource
	private TbItemParamItemMapper itemParamItemMapper;
	
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
	
	/**
	 * 取商品描述
	 */
	@Override
	public Result getItemDesc(long itemId) {
		
		/*
		 * 从缓存中取
		 */
		try {
			
			String json = redisDao.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			//判断是否为空
			if(!StringUtils.isBlank(json)){
				//数据转成java对象
				TbItemDesc itemDesc = JsonUtil.jsonToPojo(json, TbItemDesc.class);
				//返回
				return Result.ok(itemDesc);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * 缓存中不存在==>从数据库中取
		 */
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		
		/*
		 * 将数据写入缓存
		 */
		try {
			//将itemDesc转成json字符串
			String json = JsonUtil.objectToJson(itemDesc);
			//写入redis
			redisDao.set(REDIS_ITEM_KEY+":" + itemId +":desc", json);
			//设置过期时间
			redisDao.expire(REDIS_ITEM_KEY+":" + itemId +":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Result.ok(itemDesc);
	}

	@Override
	public Result getItemParam(long itemId) {
		/*
		 * 从缓存中取
		 */
		try {
			
			String json = redisDao.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//判断是否为空
			if(!StringUtils.isBlank(json)){
				//数据转成java对象
				TbItemParamItem paramItem = JsonUtil.jsonToPojo(json, TbItemParamItem.class);
				//返回
				return Result.ok(paramItem);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * 缓存中不存在==>从数据库中取
		 */
		TbItemParamItemExample example=new TbItemParamItemExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size()>0){
			TbItemParamItem paramItem = list.get(0);
			
			/*
			 * 添加缓存
			 */
			try {
				//将item转成json字符串
				String json = JsonUtil.objectToJson(paramItem);
				//写入redis
				redisDao.set(REDIS_ITEM_KEY+":" + itemId +":param", json);
				//设置过期时间
				redisDao.expire(REDIS_ITEM_KEY+":" + itemId +":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return Result.ok(paramItem);
		}
		
		
		return Result.build(400, "无此商品");
	}

}
