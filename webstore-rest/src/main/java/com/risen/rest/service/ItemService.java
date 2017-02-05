package com.risen.rest.service;

import com.risen.common.utils.Result;
/**
 * 商品详情页面展示service接口定义
 * @author sen
 *
 */
public interface ItemService {
	
	/**
	 * 取商品基本信息
	 * @param itemId
	 * @return
	 */
	Result getItemBaseInfo(long itemId);
}
