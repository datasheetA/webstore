package com.risen.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.pojo.EUIDataGridResult;
import com.risen.service.ItemParamService;

/**
 * 商品规格参数模板管理
 * @author sen
 *
 */
@Controller
public class ItemParamController {
	
	@Resource
	private ItemParamService itemParamService;
	
	/**
	 * 获取规格参数模板列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EUIDataGridResult getItemParamList(Integer page,Integer rows){
		return itemParamService.getItemParamList(page, rows);
	}
}
