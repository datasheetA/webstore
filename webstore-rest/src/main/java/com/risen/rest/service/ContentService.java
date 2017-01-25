package com.risen.rest.service;

import java.util.List;

import com.risen.pojo.TbContent;

/**
 * 首页展示内容服务
 * @author sen
 *
 */
public interface ContentService {
	
	/**
	 * 获取内容列表
	 * @param categoryId
	 * @return
	 */
	List<TbContent> getContentList(long categoryId);
}
