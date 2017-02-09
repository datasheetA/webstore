package com.risen.portal.service;

import com.risen.pojo.TbUser;

/**
 * 用户管理service 接口定义
 * @author sen
 *
 */
public interface UserService {
	
	/**
	 * 根据token调用sso接口取用户登录信息
	 * @param token
	 * @return
	 */
	TbUser getUserByToken(String token);
	
	/**
	 * 获取sso登录页面url
	 * @return
	 */
	String getLoginUrl();
}
