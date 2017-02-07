package com.risen.portal.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.pojo.TbItem;
import com.risen.portal.pojo.ItemInfo;
import com.risen.portal.service.ItemService;

/**
 * 商品详情页面展示 控制层
 * @author sen
 *
 */
@Controller
public class ItemController {
	
	@Resource
	private ItemService itemService;
	
	/**
	 * 展示商品基本信息
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	public String showItemBase(@PathVariable Long itemId,Model model){
		ItemInfo item = itemService.getItemBaseInfo(itemId);
		model.addAttribute("item",item);
		
		return "item";
	}
	
	/**
	 * 展示商品描述
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showItemDesc(@PathVariable Long itemId){
		String desc=itemService.getItemDesc(itemId);
		return desc;
	}
	
	/**
	 * 展示商品规格参数
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showItemParam(@PathVariable Long itemId){
		String param=itemService.getItemParam(itemId);
		return param;
	}
	
}
