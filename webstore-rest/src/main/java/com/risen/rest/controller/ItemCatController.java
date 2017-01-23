package com.risen.rest.controller;


import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.utils.JsonUtil;
import com.risen.rest.pojo.CatResult;
import com.risen.rest.service.ItemCatService;

/**
 * 首页商品分类管理
 * @author sen
 *
 */
@Controller
public class ItemCatController {
	
	@Resource
	private ItemCatService itemCatService;
	
	/**
	 * 返回分类列表的json格式数据 方案一
	 * @param callback
	 * @return
	 */
	/*@RequestMapping(value="/itemcat/list",
			produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback){
		CatResult catResult = itemCatService.getItemCatList();
		//把pojo转换成字符串
		String json=JsonUtil.objectToJson(catResult);
		//拼装返回值
		String result =callback+"("+json+");";
		return result;
	}*/
	
	/**
	 * 返回分类列表的json格式数据 方案二
	 * @param callback
	 * @return
	 */
	@RequestMapping("itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callback){
		CatResult catResult = itemCatService.getItemCatList();
		MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(catResult);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
	
}
