package com.risen.portal.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * 从redis中取用户购物车全部信息
	 */
	Map<String,String> redisGetCartMap(String userId);
	/**
	 * 从redis中取用户购物车信息，并转换成List
	 */
	List<CartItem> redisGetCartList(String userId);
	/**
	 * 根据商品id 从cookie中删除单条购物车商品
	 */
	void deleteInCookie(long itemId,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 根据商品id 从redis中删除单条购物车商品
	 */
	void deleteInRedis(String userId,long itemId);
	/**
	 * 用户登录后将用户cookie中的购物车信息同步到redis
	 */
	void syncCart(String userId,String cart);
	/**
	 * 根据多个id取出购物车条目列表
	 */
	List<CartItem> getCartByIds(String ids,String userId);
	
}
