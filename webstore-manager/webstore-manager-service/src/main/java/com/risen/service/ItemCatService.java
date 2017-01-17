package com.risen.service;

import java.util.List;

import com.risen.common.pojo.EUITreeNode;

public interface ItemCatService {
	
	/**
	 * 获取分类列表
	 * @param parentid
	 * @return
	 */
	List<EUITreeNode> getCatList(long parentId);
}
