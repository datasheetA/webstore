package com.risen.portal.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.risen.portal.pojo.SolrSearchResult;
import com.risen.portal.service.SearchService;

/**
 * 搜索服务调用的控制层
 * @author sen
 *
 */
@Controller
public class SearchController {
	
	@Resource
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,
						 @RequestParam(defaultValue="1")Integer page,Model model){
		//处理参数乱码
		if(queryString!=null){
			try {
				queryString=new String(queryString.getBytes("iso8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		//调用service进行搜索服务调用
		SolrSearchResult searchResult = searchService.search(queryString, page);
		//向页面传递参数
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getTotalPage());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		
		return "search";
	}
	
}
