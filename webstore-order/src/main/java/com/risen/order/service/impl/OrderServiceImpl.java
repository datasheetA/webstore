package com.risen.order.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;

import com.risen.common.dao.RedisDao;
import com.risen.common.utils.Result;
import com.risen.mapper.TbOrderItemMapper;
import com.risen.mapper.TbOrderMapper;
import com.risen.mapper.TbOrderShippingMapper;
import com.risen.order.pojo.OrderInfo;
import com.risen.order.service.OrderService;
import com.risen.pojo.TbOrderItem;
import com.risen.pojo.TbOrderShipping;

public class OrderServiceImpl implements OrderService {
	
	//订单号
	@Value("${ORDER_NO}")
	private String ORDER_NO;
	
	//订单明细表id
	@Value("${ORDER_DETAIL_ID}")
	private String ORDER_DETAIL_ID;
	
	@Resource
	private RedisDao redisDao;
	
	@Resource
	private TbOrderMapper orderMapper;
	
	@Resource
	private TbOrderItemMapper orderItemMapper;
	
	@Resource
	private TbOrderShippingMapper orderShippingMapper;
	
	
	/**
	 * 生成订单
	 */
	@Override
	public Result createOrder(OrderInfo orderInfo) {
		/*
		 * 1.向订单表添加记录
		 */
		//从redis中生成订单号
		long orderId = redisDao.incr(ORDER_NO);
		//补全数据
		orderInfo.setOrderId(orderId + "");
		//状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		//0==>未评价, 1==>已评价
		orderInfo.setBuyerRate(0);
		//向订单表插入数据
		orderMapper.insert(orderInfo);
		
		/*
		 * 2.向订单明细表添加记录
		 */
		for(TbOrderItem oi:orderInfo.getOrderItems()){
			//从redis中生成订单明细的id
			long orderItemId = redisDao.incr(ORDER_DETAIL_ID);
			//补全数据
			oi.setId(orderItemId + "");
			oi.setOrderId(orderId + ""); 
			//向订单明细插入记录
			orderItemMapper.insert(oi);
		}
		
		/*
		 * 3.向物流表添加记录
		 */
		TbOrderShipping orderShipping=orderInfo.getOrderShipping();
		//补全数据
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		//向物流表中插入记录
		orderShippingMapper.insert(orderShipping);
		
		/*
		 * 4.返回
		 */
		return Result.ok(orderId);
	}
	
	

}
