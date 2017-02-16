package com.risen.portal.pojo;

import java.util.List;

import com.risen.pojo.TbOrder;
import com.risen.pojo.TbOrderItem;
import com.risen.pojo.TbOrderShipping;

/**
 * 订单相关信息对应的pojo
 * @author sen
 *
 */
public class OrderInfo extends TbOrder{
	
	private List<TbOrderItem> orderItems;
	
	private TbOrderShipping orderShipping;

	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
	
}
