package com.risen.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.risen.common.utils.HttpClientUtil;
import com.risen.common.utils.Result;
import com.risen.portal.pojo.SolrSearchResult;
import com.risen.portal.service.SearchService;
/**
 * 搜索服务调用的service层
 * @author sen
 *
 */
@Service
public class SearchServiceImpl implements SearchService {
	
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	/**
	 * 调用搜索服务
	 */
	@Override
	public SolrSearchResult search(String queryString, int page) {
		
		//请求参数
		Map<String,String> params=new HashMap<String,String>();
		params.put("q", queryString);
		params.put("page", page+"");
		
		try{
			//httpClient调用
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL,params);
			//把json转换成java对象
			Result result = Result.formatToPojo(json, SolrSearchResult.class);
			
			if(result.getStatus()==200){
				SolrSearchResult searchResult = (SolrSearchResult) result.getData();
				return searchResult;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
