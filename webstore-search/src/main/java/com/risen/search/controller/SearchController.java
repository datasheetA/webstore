package com.risen.search.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.common.utils.ExceptionUtil;
import com.risen.common.utils.Result;
import com.risen.search.pojo.SolrSearchResult;
import com.risen.search.service.SearchService;

import java.io.UnsupportedEncodingException;

/**
 * 搜索服务控制层
 * @author sen
 *
 */
@Controller
public class SearchController {
	
	@Resource
	private SearchService searchService;
	
	
	@RequestMapping(value="/query",method=RequestMethod.GET)
	@ResponseBody
	public Result search(@RequestParam("q")String queryString,
						 @RequestParam(defaultValue="1")Integer page,
						 @RequestParam(defaultValue="60")Integer rows){
		//查询条件不能为空
		if(StringUtils.isBlank(queryString)){
			return Result.build(400,"查询条件不能为空");
		}
		SolrSearchResult searchResult=null;
		try {
			queryString=new String(queryString.getBytes("iso8859-1"),"utf-8");
			searchResult = searchService.search(queryString, page, rows);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Result.ok(searchResult);
	}
	
}
