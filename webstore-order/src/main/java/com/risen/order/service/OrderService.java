package com.risen.order.service;

import com.risen.common.utils.Result;
import com.risen.order.pojo.OrderInfo;

public interface OrderService {
	
	/**
	 * 生成订单
	 * @param orderInfo
	 */
	Result createOrder(OrderInfo orderInfo);
}
