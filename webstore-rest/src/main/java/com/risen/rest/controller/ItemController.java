package com.risen.rest.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.utils.Result;
import com.risen.rest.service.ItemService;

/**
 * 商品详情页面展示控制层
 * @author sen
 *
 */
@Controller
@RequestMapping("/detail")
public class ItemController {
	
	@Resource
	private ItemService itemService;
	
	/**
	 * 取商品基本信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public Result getItemBaseInfo(@PathVariable Long itemId){
		Result result = itemService.getItemBaseInfo(itemId);
		return result;
	}
}
