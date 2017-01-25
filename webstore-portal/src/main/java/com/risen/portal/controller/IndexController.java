package com.risen.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risen.portal.service.ContentService;


/**
 * 首页展示controller
 * @author sen
 *
 */
@Controller
public class IndexController {
	
	@Resource
	private ContentService contentService;
	
	/**
	 * 跳转到首页
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model model){
		String adJson = contentService.getContentList();
		model.addAttribute("ad1",adJson);
		return "index";
	}
}
