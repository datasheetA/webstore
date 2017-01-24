package com.risen.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.pojo.EUITreeNode;
import com.risen.common.utils.Result;
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
	
	/**
	 * 查询内容分类列表
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EUITreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		List<EUITreeNode> list=contentCategorySerivice.getContentCatList(parentId);
		return list;
	}
	
	/**
	 * 新增内容分类
	 */
	@RequestMapping("/create")
	@ResponseBody
	public Result createContentCategory(Long parentId,String name){
		Result result = contentCategorySerivice.insertContentCategory(parentId, name);
		return result;
		
	}
	
	/**
	 * 删除内容分类
	 * @param parentId
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Result deleteContentCategory(Long id){
		Result result = contentCategorySerivice.deleteContentCategory(id);
		return result;
		
	}
	
	/**
	 * 重命名内容分类
	 * @param id
	 * @param name
	 */
	@RequestMapping("/update")
	public void renameContentCategory(Long id,String name){
		contentCategorySerivice.renameContentCategory(id, name);
	}
}
