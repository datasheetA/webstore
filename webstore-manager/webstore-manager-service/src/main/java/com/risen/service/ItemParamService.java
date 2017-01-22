package com.risen.service;

import com.risen.common.pojo.EUIDataGridResult;

public interface ItemParamService {
	
	
	/**
	 * 查询规格参数模板 列表
	 */
	EUIDataGridResult getItemParamList(int page, int rows);
}
