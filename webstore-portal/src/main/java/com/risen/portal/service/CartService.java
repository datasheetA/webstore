package com.risen.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.risen.common.utils.Result;
import com.risen.portal.pojo.CartItem;

/**
 * 购物车管理 service层接口定义
 * @author sen
 *
 */
public interface CartService {
	
	
	/**
	 * 添加商品入购物车（用户已登录）
	 */
	void redisAddCartItem(long itemId,int num,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 添加商品入购物车（用户未登录）
	 */
	void cookieAddCartItem(long itemId,int num,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 从cookie中取购物车列表
	 */
	List<CartItem> cookieGetCartList(HttpServletRequest request);
	/**
	 * 从redis中取购物车列表
	 */
	List<CartItem> redisGetCartList(String userId);
	/**
	 * 根据商品id 从cookie中删除单条购物车商品
	 */
	void deleteInCookie(long itemId,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 根据商品id 从redis中删除单条购物车商品
	 */
	void deleteInRedis(String userId,long itemId,HttpServletRequest request,HttpServletResponse response);
}
