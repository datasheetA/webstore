package com.risen.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 订单管理控制层
 * @author sen
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	/**
	 * 准备订单（供用户确认商品以及填写配送信息等）
	 * @param ids
	 */
	@RequestMapping("order-cart")
	public void createOrder(String ids){
		
		//调用service查出用户选中的购物车商品展示到页面
		
		//根据用户id查出用户的配送信息
	}
}
