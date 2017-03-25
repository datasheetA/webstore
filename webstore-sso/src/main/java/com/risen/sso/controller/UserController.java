package com.risen.sso.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.utils.ExceptionUtil;
import com.risen.common.utils.Result;
import com.risen.pojo.TbUser;
import com.risen.sso.service.UserService;

/**
 * 用户管理
 * @author sen
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback){
		
		
		//参数有效性验证
		//验证param
		if(StringUtils.isBlank(param)){
			Result result=Result.build(400, "校验内容不能为空");
			return handleCallback(result, callback);
		}
		//验证type
		if(type == null){
			Result result=Result.build(400, "校验内容类型不能为空");
			return handleCallback(result, callback);
		}
		if(type != 1 && type != 2 && type != 3){
			Result result=Result.build(400, "校验内容类型错误");
			return handleCallback(result, callback);
		}
		
		//调用service
		Result result = userService.checkData(param, type);
		return handleCallback(result, callback);
		
	}
	
	/**
	 * 处理jsonp
	 * @param result
	 * @param callback
	 * @return
	 */
	private Object handleCallback(Result result,String callback){
		if(StringUtils.isBlank(callback)){
			return result;
		}
		MappingJacksonValue mjv=new MappingJacksonValue(result);
		mjv.setJsonpFunction(callback);
		return mjv;
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public Result userRegister(TbUser user){
		
		Result result= userService.userRegister(user);
		return result;
	}
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Result userLogin(String username,String password,
				  HttpServletRequest request,HttpServletResponse response){
		//调用service
		Result result=userService.userLogin(username, password, request, response);
		return result;

	}
	
	/**
	 * 根据token从redis中查询用户信息
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback){
		//执行查询
		Result result=userService.getUserByToken(token);
		//处理jsonp
		return handleCallback(result, callback);
	}
	
	/**
	 * 用户退出
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public Object userLogout(@PathVariable String token,String callback){
		//调用service
		Result result = userService.userLogout(token);
		//处理jsonp
		return handleCallback(result, callback);
	}
}
