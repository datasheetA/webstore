package com.risen.search.service;

import com.risen.search.pojo.SolrSearchResult;

/**
 * 搜索服务service层接口定义
 * @author sen
 *
 */
public interface SearchService {
	
	/**
	 * @param queryString 查询条件
	 * @param page 分页条件
	 * @param rows 分页条件
	 * @return
	 */
	SolrSearchResult search(String queryString,int page,int rows);
}
