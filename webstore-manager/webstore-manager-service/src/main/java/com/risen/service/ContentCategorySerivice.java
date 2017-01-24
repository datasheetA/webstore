package com.risen.service;

import java.util.List;

import com.risen.common.pojo.EUITreeNode;
import com.risen.common.utils.Result;

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
	
	/**
	 * 新增内容分类
	 * @param parentId
	 * @param name
	 * @return
	 */
	Result insertContentCategory(long parentId,String name);
	
	/**
	 * 删除内容分类
	 * @param parentId
	 * @param name
	 * @return
	 */
	Result deleteContentCategory(long id);
	
	/**
	 * 重命名内容分类
	 * @param id
	 * @param name
	 */
	void renameContentCategory(long id,String name);
}
