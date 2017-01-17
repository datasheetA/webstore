package com.risen.service;

import com.risen.common.pojo.EUIDataGridResult;
import com.risen.pojo.TbItem;

public interface ItemService {
	
	/**
	 * 根据商品id查询商品
	 */
	TbItem getItemById(long itemId);
	
	/**
	 * 分页查询商品列表
	 */
	EUIDataGridResult getItemList(int page,int rows); 
}
