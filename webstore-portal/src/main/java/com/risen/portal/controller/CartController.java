package com.risen.portal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.risen.portal.pojo.CartItem;
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
		return "redirect:/cart/addSuccess.html";
	}
	
	/**
	 * 跳转到商品加入购物车成功提示页面
	 * @return
	 */
	@RequestMapping("/addSuccess")
	public String toCartSuccess(){
		return "cartSuccess";
	}
	
	/**
	 * 展示购物车
	 */
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request,Model model){
		
		//购物车列表
		List<CartItem> cartList=null;
		
		//从request中取用户id
		Object obj = request.getAttribute("userId");
		//判断用户是否登录
		if(obj == null){
			//用户未登录，从cookie中取购物车信息
			cartList=cartService.cookieGetCartList(request);
		}else{
			//用户已登录，从redis中取购物车信息
			cartList=cartService.redisGetCartList(obj.toString());
		}
		
		//将购物车信息传递给页面
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	/**
	 * 删除购物车商品
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable long itemId,HttpServletRequest request,HttpServletResponse response){
		//从request中取用户id
		Object obj = request.getAttribute("userId");
		//判断用户是否登录
		if(obj == null ){
			//用户未登录，操作cookie
			cartService.deleteInCookie(itemId, request, response);
		}else{
			//用户已登录，操作redis
			cartService.deleteInRedis(obj.toString(), itemId, request, response);
		}
		return "redirect:/cart/cart.html";
	}
	
	/**
	 * 用户登录后将用户cookie中的购物车信息同步到redis
	 */
	@RequestMapping("/sync")
	public void syncCart(String userId,String cart){
		
		//调用service
		cartService.syncCart(userId, cart);
		
	}
}
