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
	@RequestMapping("/base/{itemId}")
	@ResponseBody
	public Result getItemBaseInfo(@PathVariable Long itemId){
		Result result = itemService.getItemBaseInfo(itemId);
		return result;
	}
	
	/**
	 * 取商品描述
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public Result getItemDesc(@PathVariable Long itemId){
		Result result = itemService.getItemDesc(itemId);
		return result;
	}
	
	/**
	 * 取商品规格参数
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public Result getItemParam(@PathVariable Long itemId){
		Result result = itemService.getItemParam(itemId);
		return result;
	}
	
	
}
