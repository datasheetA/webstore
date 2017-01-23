package com.risen.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 首页展示controller
 * @author sen
 *
 */
@Controller
public class IndexController {
	
	@RequestMapping("/index")
	public String showIndex(){
		return "index";
	}
}
