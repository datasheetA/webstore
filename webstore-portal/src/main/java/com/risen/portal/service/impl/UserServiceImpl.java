package com.risen.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.risen.common.utils.HttpClientUtil;
import com.risen.common.utils.Result;
import com.risen.pojo.TbUser;
import com.risen.portal.service.UserService;

/**
 * 用户管理service
 * @author sen
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;
	
	@Value("${SSO_PAGE_LOGIN}")
	private String SSO_PAGE_LOGIN;
	
	/**
	 * 根据token调用sso接口取用户信息
	 */
	@Override
	public TbUser getUserByToken(String token) {
		try {
			// 调用sso接口
			String json = HttpClientUtil.doGet(SSO_USER_TOKEN+token);
			//转成Result对象
			Result result = Result.formatToPojo(json, TbUser.class);
			if(result != null && result.getStatus() == 200){
				TbUser user = (TbUser) result.getData();
				return user;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取sso登录页面链接
	 * @return
	 */
	public String getLoginUrl(){
		return SSO_PAGE_LOGIN;
	}

}
