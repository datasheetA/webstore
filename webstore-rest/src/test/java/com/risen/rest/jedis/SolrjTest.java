package com.risen.rest.jedis;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjTest {
	
	@Test
	public void addDocument() throws Exception{
		//创建一个连接
		SolrServer server=new HttpSolrServer("http://192.168.1.114:8080/solr-4.10.3");
		//创建文档对象
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id","test001");
		document.addField("item_desc","test");
		document.addField("item_title","test");
		document.addField("item_price",123);
		//把文档对象写入索引库
		server.add(document);
		//提交
		server.commit();
		
	}
	
	@Test
	public void deleteDocument() throws Exception{
		//创建一个连接
		SolrServer server=new HttpSolrServer("http://192.168.1.114:8080/solr-4.10.3");
		//server.deleteById("test001");
		server.deleteByQuery("*:*");
		server.commit();		
	}
	
	@Test
	public void queryDocument() throws Exception{
		//创建一个连接
		SolrServer server=new HttpSolrServer("http://192.168.1.114:8080/solr-4.10.3");
		//创建一个查询对象
		SolrQuery query=new SolrQuery();
		//设置查询条件
		query.setQuery("*:*");
		//设置分页参数
		query.setStart(20);
		query.setRows(50);
		
		//执行查询
		QueryResponse response = server.query(query);
		//取查询结果列表
		SolrDocumentList list = response.getResults();
		System.out.println("共查询到"+list.getNumFound()+"条记录");
		for (SolrDocument solrDocument : list) {
			System.out.println(solrDocument.get("id"));
			
		}
		
	}
}
