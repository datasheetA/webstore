package com.risen.rest.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.utils.Result;
import com.risen.rest.service.RedisSyncService;

/**
 * redis缓存同步控制层
 * @author sen
 *
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisSyncController {
	@Resource
	private RedisSyncService redisSyncService;
	
	@RequestMapping("/content/{categoryId}")
	@ResponseBody
	public Result contentCacheSync(@PathVariable long categoryId){
		Result result = redisSyncService.syncContent(categoryId);
		return result;
	}
}
