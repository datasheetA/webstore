package com.risen.search.service.impl;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import com.risen.search.dao.SearchDao;
import com.risen.search.pojo.SolrSearchResult;
import com.risen.search.service.SearchService;

/** 
 * 搜索服务service层实现类
 * @author sen
 *
 */
@Service
public class SearchServiceImpl implements SearchService {
	
	@Resource
	private SearchDao searchDao;
	
	@Override
	public SolrSearchResult search(String queryString, int page, int rows) throws Exception {
		//创建查询对象
		SolrQuery solrQuery=new SolrQuery();
		//设置查询条件
		solrQuery.setQuery(queryString);
		//设置分页条件
		solrQuery.setStart((page-1)*rows);
		solrQuery.setRows(rows);
		//设置默认搜索域
		solrQuery.set("df","item_keywords");
		//设置高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		
		//执行查询
		SolrSearchResult searchResult = searchDao.search(solrQuery);
		//计算总页数
		long numFound=searchResult.getNumFound();
		long totalPage=numFound/rows;
		if(numFound % rows>0){
			totalPage++;
		}
		//设置总页数
		searchResult.setTotalPage(totalPage);
		//设置当前页
		searchResult.setCurrentPage(page);
		
		return searchResult;
	}

}
