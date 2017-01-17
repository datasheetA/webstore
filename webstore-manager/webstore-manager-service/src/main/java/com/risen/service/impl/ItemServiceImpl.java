package com.risen.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risen.common.pojo.EUIDataGridResult;
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
	private List<TbItem> selectByExample;
	
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

	@Override
	public EUIDataGridResult getItemList(int page, int rows) {
		
		//创建查询条件对象
		TbItemExample example =new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		
		//EasyUI的结果对象
		EUIDataGridResult result=new EUIDataGridResult();
		//设置属性
		result.setRows(list);
		//获取记录总数
		PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(list);
		result.setTotal(pageInfo.getTotal());
		//返回
		return result;
	}

}
