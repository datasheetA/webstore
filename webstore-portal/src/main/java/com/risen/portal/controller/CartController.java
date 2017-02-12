package com.risen.portal.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.risen.portal.service.CartService;

/**
 * 购物车控制层
 * @author sen
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Resource
	private CartService cartService;
	
	/**
	 * 商品加入购物车
	 */
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable long itemId,@RequestParam(defaultValue="1") int num,
							HttpServletRequest request,HttpServletResponse response){
		//从request中取用户id
		Object obj = request.getAttribute("userId");
		//判断用户是否登录
		if(obj == null){
			//用户未登录,将购物车信息写入cookie
			cartService.cookieAddCartItem(itemId, num, request, response);
			
		}else{
			//用户已登录,将购物车信息写入redis
			cartService.redisAddCartItem(itemId, num, request, response);
		}
		//添加成功，返回提示页面
		return "cartSuccess";
	}
}
