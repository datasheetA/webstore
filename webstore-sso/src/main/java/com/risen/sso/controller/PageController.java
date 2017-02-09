package com.risen.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转controller
 * @author sen
 *
 */
@Controller
@RequestMapping("/page")
public class PageController {
	
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping("/register")
	public String toRegister(){
		return "register";
	}
	
	/**
	 * 跳转到登录页面
	 * @param redirect 登录成功后回调的url
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String toLogin(String redirect,Model model){
		model.addAttribute("redirect", redirect);
		return "login";
	}
}
