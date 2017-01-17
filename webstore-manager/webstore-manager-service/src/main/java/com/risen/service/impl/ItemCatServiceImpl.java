package com.risen.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.risen.common.pojo.EUITreeNode;
import com.risen.mapper.TbItemCatMapper;
import com.risen.pojo.TbItemCat;
import com.risen.pojo.TbItemCatExample;
import com.risen.pojo.TbItemCatExample.Criteria;
import com.risen.service.ItemCatService;

/**
 * 商品分类管理实现
 * @author sen
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Resource
	private TbItemCatMapper itemCatMapper;
	
	/** 
	 * 获取商品分类列表
	 */
	@Override
	public List<EUITreeNode> getCatList(long parentId) {
		
		//创建查询条件
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//根据查询条件查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//将结果转换成List<EUITreeNode>
		List<EUITreeNode> resultList=new ArrayList<EUITreeNode>();
		for (TbItemCat itemCat : list) {
			EUITreeNode node =new EUITreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			node.setState(itemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}
	

}
