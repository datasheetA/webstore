package com.risen.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	//购物车信息保存在cookie中的有效期
	@Value("${CART_COOKIE_MAXAGE}")
	private int CART_COOKIE_MAXAGE;
	
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
		
		//从request中取用户id
		String userId=(String) request.getAttribute("userId");
		
		//从redis中取购物车信息
		Map<String, String> cartMap = redisGetCartMap(userId);
		
		//检查此商品是否已存在与购物车，如果存在改变其数量即可，
		//见函数redisItemExist(Map<String,String> map,long itemId,int num)
		boolean flag = redisItemExist(cartMap, itemId, num);
		
		//当购物车为空 或者购物车无此商品，将此商品加入购物车
		if(!flag){
			//创建购物车商品条目
			CartItem cartItem=getCartItem(itemId, num);
			//加入购物车
			cartMap.put(itemId+"", JsonUtil.objectToJson(cartItem));
		}
		
		//将用户购物车重新写入redis
		redisDao.hmset(CART_REDIS_KEY + userId,cartMap);
		
	}
	
	/**
	 * 判断目标商品在redis购物车中是否已存在
	 * @return
	 */
	private boolean redisItemExist(Map<String,String> map,long itemId,int num){
		
		//根据id从购物车中取
		String json = map.get(itemId);
		if(!StringUtils.isBlank(json)){
			CartItem item = JsonUtil.jsonToPojo(json, CartItem.class);
			//更改购物车中商品的数量
			item.setNum(item.getNum()+num);
			//重新写入购物车
			map.put(itemId+"", JsonUtil.objectToJson(item));
			//商品在购物车中已存在
			return true;
		}
		return false;
	}
	
	/**
	 * 根据itemId和num得到一个cartItem对象
	 * @param itemId
	 * @return
	 */
	private CartItem getCartItem(long itemId,int num){
		
		CartItem cartItem =new CartItem();
		//根据商品id调用rest服务查询商品信息
		String json = HttpClientUtil.doGet(ITEM_BASE_URL + itemId);
		//转换为Result对象
		Result result = Result.formatToPojo(json, TbItem.class);
		if(result != null && result.getStatus()==200){
			TbItem item=(TbItem) result.getData();
			//设置属性值
			cartItem.setId(item.getId());
			cartItem.setImage(item.getImage().split(",")[0]);
			cartItem.setNum(num);
			cartItem.setPrice(item.getPrice());
			cartItem.setTitle(item.getTitle());
		}
		return cartItem;
	}
	
	
	/**
	 * 添加商品入购物车，用户未登录
	 */
	@Override
	public void cookieAddCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		
		//从cookie中取购物车信息
		List<CartItem> list = cookieGetCartList(request);
		
		//检查此商品是否已存在于购物车，如果存在改变其数量即可，
		//见函数itemExist(List<CartItem> list,long itemId,boolean flag)
		boolean flag=itemExist(list, itemId, num);
		
		//当购物车为空 或者购物车无此商品，将此商品加入购物车
		if(!flag){
			//创建一个购物车条目
			CartItem cartItem = getCartItem(itemId, num);
			//加入购物车
			list.add(cartItem);
			
		}
		//将购物车重新写入cookie
		CookieUtils.setCookie(request, response,CART_COOKIE_KEY, JsonUtil.objectToJson(list), CART_COOKIE_MAXAGE);
	}
	
	/**
	 * 从cookie中取购物车列表
	 */
	@Override
	public List<CartItem> cookieGetCartList(HttpServletRequest request) {
		// 取出购物车信息的json串
		String json = CookieUtils.getCookieValue(request, CART_COOKIE_KEY);
		//判断
		if(!StringUtils.isBlank(json)){
			List<CartItem> list = JsonUtil.jsonToList(json, CartItem.class);
			return list;
		}
		return new ArrayList<CartItem>();
	}
	
	/**
	 * 从redis中取购物车列表
	 */
	@Override
	public Map<String,String> redisGetCartMap(String userId) {
		// 从redis中取用户购物车信息
		Map<String, String> cartMap = redisDao.hgetAll(CART_REDIS_KEY + userId);
		//判断购物车是否为空
		if(cartMap != null && cartMap.size()>0){
			return cartMap;
		}
		return new HashMap<String,String>();
	}
	
	/**
	 * 判断购物车是否存在指定商品
	 */
	private boolean itemExist(List<CartItem> list,long itemId,int num){
		
		//标记:用来判断商品是否已经在购物车中存在
		boolean flag=false;
		//判断购物车是否为空
		if( list.size()>0){
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
	 * 从cookie中删除购物车商品
	 * @param itemId
	 * @param request
	 * @param response
	 */
	@Override
	public void deleteInCookie(long itemId,HttpServletRequest request,HttpServletResponse response) {
		
		//取出购物车信息 
		List<CartItem> list = cookieGetCartList(request);
		//删除指定商品
		for (CartItem cartItem : list) {
			if(cartItem.getId()== itemId){
				list.remove(cartItem);
				break;
			}
		}
		//将购物车重新写入cookie
		CookieUtils.setCookie(request, response,CART_COOKIE_KEY, JsonUtil.objectToJson(list), CART_COOKIE_MAXAGE);
	}
	
	/**
	 * 从redis中删除购物车商品
	 * @param itemId
	 * @param request
	 * @param response
	 */
	@Override
	public void deleteInRedis(String userId,long itemId) {
		// 从redis中取出购物车列表
		Map<String, String> cartMap = redisGetCartMap(CART_REDIS_KEY + userId);
		//删除指定商品
		cartMap.remove(itemId);
		//将购物车重新写入redis
		redisDao.hmset(CART_REDIS_KEY + userId, cartMap);
		
	}
	
	/**
	 * 用户登录后将用户cookie中的购物车信息同步到redis
	 */
	@Override
	public void syncCart(String userId,String cart) {
		//从cookie中取购物车信息
		List<CartItem> list=null;
		if(StringUtils.isBlank(cart)){
			list=new ArrayList<CartItem>();
		}else{
			list=JsonUtil.jsonToList(cart, CartItem.class);
		}
		//从redis中取购物车信息
		Map<String,String> map= redisGetCartMap(userId);
		//如果cookie中有购物车信息则进行同步
		if(list.size()>0){
			for (CartItem cartItem : list) {
				map.put(cartItem.getId()+"", JsonUtil.objectToJson(cartItem));
			}
		}
		
	}
	
	/**
	 * 从redis中取用户购物车信息。并转换成list
	 */
	@Override
	public List<CartItem> redisGetCartList(String userId) {
		//取出购物车
		Map<String, String> cartMap = redisGetCartMap(userId);
		//将map转换成list以供页面渲染
		List<CartItem> cartList=new ArrayList<CartItem>();
		if(cartMap.size()>0){
			CartItem cartItem=null;
			for(Entry<String,String> e:cartMap.entrySet()){
				cartItem=JsonUtil.jsonToPojo(e.getValue(), CartItem.class);
				cartList.add(cartItem);
			}
		}
		return cartList;
	}
	
	/**
	 * 根据多个id取购物车条目列表
	 */
	@Override
	public List<CartItem> getCartByIds(String ids, String userId) {
		
		//将多个id拼成的字符串转成数组
		String[] arr = ids.split(",");
		List<String> list = redisDao.hmget(CART_REDIS_KEY + userId, arr);
		//将list中的json格式的CartItem转成对象
		List<CartItem> cartList=new ArrayList<CartItem>();
		for(String json:list){
			cartList.add(JsonUtil.jsonToPojo(json, CartItem.class));
		}
		return cartList;
	}

}
