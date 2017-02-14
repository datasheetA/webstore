package com.risen.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.utils.Result;
import com.risen.order.pojo.OrderInfo;

/**
 * 订单管理 控制层
 * @author sen
 *
 */
@Controller
public class OrderController {
	
	/**
	 * 生成订单
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public Result createOrder(@RequestBody OrderInfo orderInfo){
		
		return null;
	}
}
