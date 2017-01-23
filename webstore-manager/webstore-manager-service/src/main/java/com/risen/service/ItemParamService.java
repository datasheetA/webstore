package com.risen.service;

import com.risen.common.pojo.EUIDataGridResult;
import com.risen.common.utils.Result;
import com.risen.pojo.TbItemParam;

public interface ItemParamService {
	
	
	/**
	 * 查询规格参数模板 列表
	 */
	EUIDataGridResult getItemParamList(int page, int rows);
	
	/**
	 * 根据商品分类查询规格参数模板
	 */
	Result getItemParamByCid(long cid);
	
	/**
	 * 新增规格参数模板
	 */
	Result insertItemParam(TbItemParam itemParam);
}
