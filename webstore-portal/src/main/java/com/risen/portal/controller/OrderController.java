package com.risen.portal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.risen.pojo.TbUser;
import com.risen.portal.pojo.CartItem;
import com.risen.portal.pojo.OrderInfo;
import com.risen.portal.service.CartService;

/**
 * 订单管理控制层
 * @author sen
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Resource
	private CartService cartService;
	
	/**
	 * 用户从购物车页面点击“去结算”后
	 * 准备订单（供用户确认商品以及填写配送信息等）
	 * @param ids
	 */
	@RequestMapping("/order-cart")
	public String prepareOrder(String ids,HttpServletRequest request,Model model){
		
		TbUser user=(TbUser) request.getAttribute("user");
		String userId=user.getId()+"";
		//调用service查出用户选中的购物车商品展示到页面
		List<CartItem> cartList = cartService.redisGetCartList(userId);
		
		model.addAttribute("cartList", cartList);
		return "order-cart";
	}
	
	/**
	 * 生成订单
	 */
	@RequestMapping("/create")
	public String createOrder(OrderInfo orderInfo,HttpServletRequest request,Model model){
		
		try {
			
			//取用户信息
			TbUser user=(TbUser) request.getAttribute("user");
			//补全订单信息
			orderInfo.setUserId(user.getId());
			orderInfo.setBuyerNick(user.getUsername());
			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "创建订单出错！请稍后重试");
			return "error/exception";
		}
	}
}
