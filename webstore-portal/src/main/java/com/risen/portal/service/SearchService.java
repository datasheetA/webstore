package com.risen.portal.service;

import com.risen.portal.pojo.SolrSearchResult;

/**
 * 搜索服务调用的service层接口定义
 * @author sen
 *
 */
public interface SearchService {
	
	SolrSearchResult search(String queryString,int page);
}
