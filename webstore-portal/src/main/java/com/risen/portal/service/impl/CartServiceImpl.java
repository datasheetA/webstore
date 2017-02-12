package com.risen.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.risen.common.dao.RedisDao;
import com.risen.common.utils.CookieUtils;
import com.risen.common.utils.HttpClientUtil;
import com.risen.common.utils.JsonUtil;
import com.risen.common.utils.Result;
import com.risen.pojo.TbItem;
import com.risen.pojo.TbUser;
import com.risen.portal.pojo.CartItem;
import com.risen.portal.service.CartService;

/**
 * 购物车service
 * @author sen
 *
 */
@Service
public class CartServiceImpl implements CartService {
	
	//购物车信息存在cookie中的key
	@Value("${CART_COOKIE_KEY}")
	private String CART_COOKIE_KEY;
	
	//购物车信息存在redis中的key
	@Value("${CART_REDIS_KEY}")
	private String CART_REDIS_KEY;
	
	//根据商品id获取商品基本信息的调用接口
	@Value("${ITEM_BASE_URL}")
	private String ITEM_BASE_URL;
	
	@Resource
	private RedisDao redisDao;
	
	
	/**
	 * 添加商品入购物车，用户已登录
	 */
	@Override
	public void redisAddCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		
		//标记:判断购物车是否已经有此商品
		boolean flag=false;
		
		//从request中取用户id
		String userId=(String) request.getAttribute("userId");
		
		//从redis中取购物车信息
		List<CartItem> list = redisGetCartList(userId);
		
		//检查此商品是否已存在与购物车，如果存在改变其数量即可，
		//见函数itemExist(List<CartItem> list,long itemId,boolean flag)
		itemExist(list, itemId, num, flag);
		
		//当购物车为空 或者购物车无此商品，将此商品加入购物车
		//见函数addItem(List<CartItem> list,long itemId,boolean flag)
		addItem(list, itemId, num, flag);
		
		//将用户购物车重新写入redis
		redisDao.set(CART_REDIS_KEY + userId, JsonUtil.objectToJson(list));
		
	}
	
	/**
	 * 添加商品入购物车，用户未登录
	 */
	@Override
	public void cookieAddCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		
		//标记:判断购物车是否已经有此商品
		boolean flag=false;
		
		//从cookie中取购物车信息
		List<CartItem> list = cookieGetCartList(request);
		
		//检查此商品是否已存在与购物车，如果存在改变其数量即可，
		//见函数itemExist(List<CartItem> list,long itemId,boolean flag)
		itemExist(list, itemId, num,flag);
		
		//当购物车为空 或者购物车无此商品，将此商品加入购物车
		//见函数addItem(List<CartItem> list,long itemId,boolean flag)
		addItem(list, itemId, num, flag);
		
		//将购物车重新写入cookie
		CookieUtils.setCookie(request, response, CART_COOKIE_KEY, JsonUtil.objectToJson(list));
	}
	
	/**
	 * 从cookie中取购物车列表
	 */
	@Override
	public List<CartItem> cookieGetCartList(HttpServletRequest request) {
		// 取出购物车信息的json串
		String json = CookieUtils.getCookieValue(request, CART_COOKIE_KEY);
		//判断
		if( !StringUtils.isBlank(json)){
			List<CartItem> list = JsonUtil.jsonToList(json, CartItem.class);
			return list;
		}
		return null;
	}
	
	/**
	 * 从redis中取购物车列表
	 */
	@Override
	public List<CartItem> redisGetCartList(String userId) {
		// 从redis中取用户购物车信息
		String json = redisDao.get(CART_REDIS_KEY + userId);
		//判断购物车是否为空
		if( !StringUtils.isBlank(json)){
			List<CartItem> list = JsonUtil.jsonToList(json, CartItem.class);
			return list;
		}
		return null;
	}
	
	/**
	 * 判断购物车是否存在指定商品
	 */
	private boolean itemExist(List<CartItem> list,long itemId,int num,boolean flag){
		
		//判断购物车是否为空
		if( list != null && list.size()>0){
			//判断购物车是否已存在此商品
			for (CartItem cartItem : list) {
				if(cartItem.getId() == itemId){
					//将购物车中此商品的数量增加即可
					cartItem.setNum(num + cartItem.getNum());
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 将目标商品加入购物车
	 */
	private void addItem(List<CartItem> list,long itemId,int num,boolean flag){
		//购物车无此商品 或者购物车为空
		if(!flag){
			//根据商品id调用rest服务查询商品信息
			String json = HttpClientUtil.doGet(ITEM_BASE_URL + itemId);
			//转换为Result对象
			Result result = Result.formatToPojo(json, TbUser.class);
			if(result != null && result.getStatus()==200){
				TbItem item=(TbItem) result.getData();
				CartItem cartItem =new CartItem();
				//设置属性值
				cartItem.setId(item.getId());
				cartItem.setImage(item.getImage().split(",")[0]);
				cartItem.setNum(num);
				cartItem.setPrice(item.getPrice());
				cartItem.setTitle(item.getTitle());
				//将此商品写入购物车
				list.add(cartItem);
			}
			
		}
	}

}
