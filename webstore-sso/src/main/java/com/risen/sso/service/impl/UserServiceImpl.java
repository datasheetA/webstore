package com.risen.sso.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.risen.common.utils.CookieUtils;
import com.risen.common.utils.JsonUtil;
import com.risen.common.utils.Result;
import com.risen.common.utils.UUIDGenerator;
import com.risen.mapper.TbUserMapper;
import com.risen.pojo.TbUser;
import com.risen.pojo.TbUserExample;
import com.risen.pojo.TbUserExample.Criteria;
import com.risen.sso.dao.RedisDao;
import com.risen.sso.service.UserService;

/**
 * 用户相关 service层
 * @author sen
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	private TbUserMapper userMapper;
	
	@Resource
	private RedisDao redisDao;
	
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	
	@Value("${USER_SESSION_EXPIRE}")
	private Integer USER_SESSION_EXPIRE;
	
	/**
	 * 检查注册信息是否可用
	 * @param data 所要检查的数据
	 * @param type 数据的类型:1-->username,2-->phone,3-->email
	 * @return
	 */
	@Override
	public Result checkData(String data, Integer type) {
		
		//查询对象
		TbUserExample example=new TbUserExample();
		//查询条件
		Criteria criteria = example.createCriteria();
		//设置查询条件
		switch(type){
				case 1:criteria.andUsernameEqualTo(data);break;
				case 2:criteria.andPhoneEqualTo(data);break;
				case 3:criteria.andEmailEqualTo(data);
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		//判断注册信息是否已被占用
		if(list != null && list.size()>0){
			//数据已被注册
			return Result.ok(false);
		}
		
		return Result.ok(true);
	}
	
	/**
	 * 用户注册
	 */
	@Override
	public Result userRegister(TbUser user) {
		// 补齐参数
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//密码md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		//执行添加
		userMapper.insert(user);
		
		return Result.ok();
	}
	
	/**
	 * 用户登录
	 */
	@Override
	public Result userLogin(String username, String password,
				  HttpServletRequest request,HttpServletResponse response) {
		//查询条件
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		//根据用户名查询用户信息
		List<TbUser> list = userMapper.selectByExample(example);
		//判断
		if(list == null || list.size()>0){
			//没有此用户
			return Result.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		//比对密码
		password=DigestUtils.md5DigestAsHex(password.getBytes());
		if(!password.equals(user.getPassword())){
			//密码错误
			return Result.build(400, "用户名或密码错误");
		}
		//用户登录信息正确
		//生成token
		String token=UUIDGenerator.getLong();
		//保存用户信息之前，将用户密码清空
		user.setPassword(null);
		//将token及用户信息写入redis
		redisDao.set(REDIS_USER_SESSION_KEY + ":" +token, JsonUtil.objectToJson(user));
		//设置session的过期时间
		redisDao.expire(REDIS_USER_SESSION_KEY + ":" +token, USER_SESSION_EXPIRE);
		
		//将token写入cookie，cookie失效时间与session相同
		CookieUtils.setCookie(request, response, "US_TOKEN", token);
		return Result.ok(token);
	}
	
	/**
	 * 根据token从redis中查询用户信息
	 */
	@Override
	public Result getUserByToken(String token) {
		//查询
		String json = redisDao.get(REDIS_USER_SESSION_KEY + ":" +token); 
		if(StringUtils.isBlank(json)){
			return Result.build(400, "无效的token");
		}
		//更新过期时间
		redisDao.expire(REDIS_USER_SESSION_KEY + ":" +token, USER_SESSION_EXPIRE);
		
		return Result.ok(JsonUtil.jsonToPojo(json, TbUser.class));
	}

}
