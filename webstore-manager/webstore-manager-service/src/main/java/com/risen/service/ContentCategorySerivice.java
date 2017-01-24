package com.risen.service;

import java.util.List;

import com.risen.common.pojo.EUITreeNode;

/**
 * 首页内容分类管理service
 * @author sen
 *
 */
public interface ContentCategorySerivice {
	
	/**
	 * 获取内容分类列表
	 * @param parentId
	 * @return
	 */
	List<EUITreeNode> getContentCatList(long parentId);
}
