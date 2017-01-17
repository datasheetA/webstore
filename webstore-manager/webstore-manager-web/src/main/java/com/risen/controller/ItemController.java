package com.risen.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId){
		TbItem item=itemServiceImpl.getItemById(itemId);
		return item;
	}
}
