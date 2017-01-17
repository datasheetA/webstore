package com.risen.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.risen.mapper.TbItemMapper;
import com.risen.pojo.TbItem;
import com.risen.pojo.TbItemExample;
import com.risen.pojo.TbItemExample.Criteria;
import com.risen.service.ItemService;

/**
 * @author sen
 *	商品管理service
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Resource
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		
		//TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		
		//1.创建查询条件对象
		TbItemExample example=new TbItemExample();
		//2.设置查询条件
		Criteria criteria=example.createCriteria();
		criteria.andIdEqualTo(itemId);
		//3.根据查询条件查询
		List<TbItem> list=itemMapper.selectByExample(example);
		//4.返回结果
		if(list!=null&&list.size()>0){
			TbItem item=list.get(0);
			return item;
		}
		return null;
	}

}
