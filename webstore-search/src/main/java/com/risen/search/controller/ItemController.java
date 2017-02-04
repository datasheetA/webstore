package com.risen.search.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.utils.Result;
import com.risen.search.service.ItemService;

@Controller
@RequestMapping("/manager")
public class ItemController {
	
	@Resource
	private ItemService itemService;
	
	/**
	 * 商品信息导入索引库
	 * @return
	 */
	@RequestMapping("/importAll")
	@ResponseBody
	public Result importAll(){
		Result result = itemService.importAll();
		return result;
	}
}
