package com.risen.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import com.risen.search.dao.SearchDao;
import com.risen.search.pojo.Item;
import com.risen.search.pojo.SolrSearchResult;

/**
 * 搜索服务dao层
 * @author sen
 *
 */
@Repository
public class SearchDaoImpl implements SearchDao {
	
	
	@Resource
	private SolrServer solrServer;
	
	/**
	 * 查询索引库
	 */
	@Override
	public SolrSearchResult search(SolrQuery solrQuery) throws Exception {
		
		//返回值对象
		SolrSearchResult result=new SolrSearchResult();
		
		//根据查询条件查询索引库
		QueryResponse queryResponse = solrServer.query(solrQuery);
		//取查询结果列表
		SolrDocumentList list = queryResponse.getResults();
		//取查询结果总数
		long numFound = list.getNumFound();
		//将结果列表转成商品列表
		//商品列表
		List<Item> itemList=new ArrayList<>();
		//取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		
		for (SolrDocument sd : list) {
			//创建一个商品对象
			Item item =new Item();
			//设置item属性
			item.setId((String) sd.get("id"));
			item.setCategory_name((String) sd.get("item_category_name"));
			item.setImage((String) sd.get("item_image"));
			item.setPrice((long) sd.get("item_price"));
			item.setSell_point((String) sd.get("item_sell_point"));
			
			//高亮显示的域
			String title="";
			
			//取高亮显示的结果
			List<String> hList = highlighting.get(sd.get("id")).get("item_title");
			if(hList!=null && hList.size()>0){
				title=hList.get(0);
			}else{
				title=(String) sd.get("item_title");
			}
			item.setTitle(title);
			
			//添加到itemList中
			itemList.add(item);
		}
		
		
		//设置返回值对象属性
		result.setItemList(itemList);
		result.setNumFound(numFound);
		
		
		return result;
	}

}
