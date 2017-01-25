package com.risen.rest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.risen.mapper.TbContentMapper;
import com.risen.pojo.TbContent;
import com.risen.pojo.TbContentExample;
import com.risen.pojo.TbContentExample.Criteria;
import com.risen.rest.service.ContentService;

/**
 * 首页展示内容服务
 * @author sen
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Resource
	private TbContentMapper contentMapper;
	
	/**
	 * 获取展示内容列表
	 */
	@Override
	public List<TbContent> getContentList(long categoryId) {
		//设置查询条件
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		
		return list;
	}

}
