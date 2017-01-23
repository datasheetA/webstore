package com.risen.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.pojo.EUIDataGridResult;
import com.risen.common.utils.Result;
import com.risen.pojo.TbItem;
import com.risen.service.ItemService;

/**
 * @author sen
 *	商品管理controller
 */
@Controller
public class ItemController {
	
	@Resource
	private ItemService itemServiceImpl;
	
	/**
	 * 根据id查询商品
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId){
		TbItem item=itemServiceImpl.getItemById(itemId);
		return item;
	}
	
	/**
	 * 后台管理查询商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EUIDataGridResult getItemList(Integer page,Integer rows){
		EUIDataGridResult result = itemServiceImpl.getItemList(page, rows);
		return result;
	}
	
	/**
	 * 后台管理新增商品
	 * @return
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public Result createItem(TbItem item,String desc,String itemParams){
		Result result=itemServiceImpl.createItem(item,desc,itemParams);
		return result;
		
	}
}
