package com.risen.rest.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.utils.ExceptionUtil;
import com.risen.common.utils.Result;
import com.risen.pojo.TbContent;
import com.risen.rest.service.ContentService;
/**
 * 首页内容展示管理
 * @author sen
 *
 */
@Controller
public class ContentController {
	
	@Resource
	private ContentService contentService;
	
	/**
	 * 根据内容分类 获取首页展示内容列表
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/content/list/{categoryId}")
	@ResponseBody
	public Result getContentList(@PathVariable Long categoryId){
		try{
			List<TbContent> list = contentService.getContentList(categoryId);
			return Result.ok(list);
		}catch(Exception e){
			e.printStackTrace();
			return Result.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
