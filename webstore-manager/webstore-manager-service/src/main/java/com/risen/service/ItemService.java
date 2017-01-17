package com.risen.service;

import com.risen.pojo.TbItem;

public interface ItemService {
	
	/**
	 * 根据商品id查询商品
	 */
	TbItem getItemById(long itemId);
}
