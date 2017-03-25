package com.risen.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.risen.search.pojo.SolrSearchResult;

public interface SearchDao {
	
	
	SolrSearchResult search(SolrQuery solrQuery);
}
