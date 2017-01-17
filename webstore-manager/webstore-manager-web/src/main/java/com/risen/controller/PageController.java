package com.risen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sen
 *	页面跳转contrllor
 */
@Controller
public class PageController {
	
	/**
	 * 打开首页
	 */
	@RequestMapping("/")
	public String showIndex(){
		
		return "index";
	}
	
	/**
	 * 打开菜单
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
	
}
