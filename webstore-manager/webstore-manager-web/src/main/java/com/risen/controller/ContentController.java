package com.risen.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.pojo.EUIDataGridResult;
import com.risen.common.utils.Result;
import com.risen.pojo.TbContent;
import com.risen.service.ContentService;

/**
 * 内容管理
 * @author sen
 *
 */
@Controller
public class ContentController {
	
	@Resource
	private ContentService contentService;
	
	/**
	 * 查询内容列表
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EUIDataGridResult getContentList(Integer page,Integer rows,Long categoryId){
		EUIDataGridResult result = contentService.getContentList(page, rows, categoryId);
		return result;
	}
	
	/**
	 * 新增内容
	 * @param content
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public Result createContent(TbContent content){
		Result result = contentService.insertContent(content);
		return result;
	}
}
