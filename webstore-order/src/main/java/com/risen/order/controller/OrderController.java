package com.risen.order.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.utils.ExceptionUtil;
import com.risen.common.utils.Result;
import com.risen.order.pojo.OrderInfo;
import com.risen.order.service.OrderService;

/**
 * 订单管理 控制层
 * @author sen
 *
 */
@Controller
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	/**
	 * 生成订单
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public Result createOrder(@RequestBody OrderInfo orderInfo){
		
		try {
			
			Result result = orderService.createOrder(orderInfo);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return Result.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
