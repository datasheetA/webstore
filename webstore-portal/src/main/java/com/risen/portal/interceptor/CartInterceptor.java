package com.risen.portal.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.risen.common.utils.CookieUtils;
import com.risen.pojo.TbUser;
import com.risen.portal.service.UserService;


/**
 * 购物车访问拦截器
 * @author sen
 *
 */
public class CartInterceptor implements HandlerInterceptor {
	
	@Resource
	private UserService userService;
	
	/**
	 * 拦截购物车请求，判断用户是否登录，为请求加上用户id
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// handler执行之前
		
		//当用户访问购物车时,判断用户是否登录
		String token = CookieUtils.getCookieValue(request, "US_TOKEN");
		//根据token调用sso接口查询用户登录信息
		TbUser user = userService.getUserByToken(token);
		//判断
		if(user != null){
			request.setAttribute("userId", user.getId());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
