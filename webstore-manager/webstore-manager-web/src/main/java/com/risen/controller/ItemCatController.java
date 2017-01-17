package com.risen.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.pojo.EUITreeNode;
import com.risen.service.ItemCatService;

/**
 * @author sen
 *	商品分类管理controller
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Resource
	private ItemCatService itemCatService;
	
	/**
	 * 查询商品分类列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EUITreeNode> getCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		List<EUITreeNode> list = itemCatService.getCatList(parentId);
		return list;
	}
}
