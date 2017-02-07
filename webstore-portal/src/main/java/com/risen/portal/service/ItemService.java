package com.risen.portal.service;

import com.risen.pojo.TbItem;
import com.risen.portal.pojo.ItemInfo;

/**
 * 商品详情页面处理 service层接口定义
 * @author sen
 *
 */
public interface ItemService {
	
	/**
	 * 取商品基本信息
	 * @param itemId
	 * @return
	 */
	ItemInfo getItemBaseInfo(long itemId);
	
	/**
	 * 取商品描述
	 * @param itemId
	 * @return
	 */
	String getItemDesc(long itemId);
	
	/**
	 * 取商品规格参数
	 * @param itemId
	 * @return
	 */
	String getItemParam(long itemId);
}
