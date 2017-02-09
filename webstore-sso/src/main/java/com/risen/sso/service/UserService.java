package com.risen.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.risen.common.utils.Result;
import com.risen.pojo.TbUser;

/**
 * 用户相关service 接口定义
 * @author sen
 *
 */
public interface UserService {
	
	
	/**
	 * 检查注册信息是否可用
	 * @param data 所要检查的数据
	 * @param type 数据的类型:1-->username,2-->phone,3-->email
	 * @return
	 */
	public Result checkData(String data,Integer type);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public Result userRegister(TbUser user);
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public Result userLogin(String username,String password,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 根据token从redis中查询用户信息
	 * @param token
	 * @return
	 */
	public Result getUserByToken(String token);
	
	/**
	 * 用户退出
	 * @param token
	 * @return
	 */
	public Result userLogout(String token);
}
