package com.risen.portal.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.risen.common.utils.CookieUtils;
import com.risen.pojo.TbUser;
import com.risen.portal.service.UserService;

/**用户登录拦截器
 * @author sen
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Resource
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// handler执行之前
		/*
		 * 判断是否登录
		 */
		//1.从cookie中取token信息
		String token = CookieUtils.getCookieValue(request, "US_TOKEN");
		//2.根据token调用sso接口查询用户登录信息
		TbUser user = userService.getUserByToken(token);
		//3.判断用户登录信息是够存在,如果不存在,跳转到登录页面,并把请求url作为参数传递给登录页面
		if(user == null){
			response.sendRedirect(userService.getLoginUrl()+"?redirect="+request.getRequestURL());
			//返回值决定handler是否执行:false==>不执行
			return false;
		}
		request.setAttribute("user", user);
		return true;
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// handler执行之后,返回ModelAndView之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回ModelAndView之后

	}

}
