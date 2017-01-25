package com.risen.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.risen.common.utils.HttpClientUtil;
import com.risen.common.utils.JsonUtil;
import com.risen.common.utils.Result;
import com.risen.pojo.TbContent;
import com.risen.portal.service.ContentService;

/**
 * @author sen
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	
	/**
	 * 获取大广告位 展示需要的内容
	 */
	@Override
	public String getContentList() {
		//调用rest服务层的服务获得数据
		String data = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
		//把json字符串转化成Result对象
		try{
			Result result = Result.formatToList(data, TbContent.class);
			//取内容列表
			List<TbContent> list = (List<TbContent>) result.getData();
			
			List<Map<String,Object>> resultList=new ArrayList<>();
			//创建一个jsp页码要求的pojo列表
			for (TbContent tbContent : list) {
				Map<String,Object> map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tbContent.getPic2());
				map.put("widthB", 550);
				map.put("heightB", 240);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				resultList.add(map);
			}
			return JsonUtil.objectToJson(resultList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
