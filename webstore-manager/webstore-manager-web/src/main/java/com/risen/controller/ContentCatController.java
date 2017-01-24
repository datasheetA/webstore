package com.risen.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.pojo.EUITreeNode;
import com.risen.service.ContentCategorySerivice;

/**
 * 首页内容分类管理
 * @author sen
 *
 */
@Controller
@RequestMapping("content/category")
public class ContentCatController {
	
	@Resource
	private ContentCategorySerivice contentCategorySerivice;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUITreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		List<EUITreeNode> list=contentCategorySerivice.getContentCatList(parentId);
		return list;
	}
}
