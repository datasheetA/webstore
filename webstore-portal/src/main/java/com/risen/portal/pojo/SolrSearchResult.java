package com.risen.portal.pojo;

import java.util.List;

public class SolrSearchResult {
	
	
	//查询结果列表
	private List<Item> itemList;
	
	//总记录数
	private long numFound;
	
	//总页数
	private long totalPage;
	
	//当前页
	private long currentPage;

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
