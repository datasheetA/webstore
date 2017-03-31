package com.risen.search.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.risen.common.utils.ExceptionUtil;
import com.risen.common.utils.Result;
import com.risen.search.mapper.ItemMapper;
import com.risen.search.pojo.Item;
import com.risen.search.service.ItemService;

/**
 * 
 * @author sen
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Resource
	private ItemMapper itemMapper;
	
	@Resource
	private SolrServer solrServer;
	
	/**
	 * 将数据导入索引库
	 */
	@Override
	public Result importAll() {
		//查询商品列表
		try {
		List<Item> list = itemMapper.getItemList();
		//循环把商品信息写入索引库
		for (Item item : list) {
			//创建一个SolrInputDocument对象
			SolrInputDocument document=new SolrInputDocument();
			document.addField("id",item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image",item.getImage());
			document.addField("item_category_name",item.getCategory_name());
			document.addField("item_desc", item.getItem_desc());
			//写入索引库
			solrServer.add(document);
		}
		//提交修改

			solrServer.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}

}
