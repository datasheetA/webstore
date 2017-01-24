package com.risen.service;

import org.springframework.stereotype.Service;

import com.risen.common.pojo.EUIDataGridResult;
import com.risen.common.utils.Result;
import com.risen.pojo.TbContent;

/**
 * 内容管理
 * @author sen
 *
 */
@Service
public interface ContentService {
	
	/**
	 * 根据分类id查询内容列表
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	EUIDataGridResult getContentList(int page,int rows,long categoryId);
	
	/**
	 * 新增内容
	 * @param content
	 * @return
	 */
	Result insertContent(TbContent content);
}
