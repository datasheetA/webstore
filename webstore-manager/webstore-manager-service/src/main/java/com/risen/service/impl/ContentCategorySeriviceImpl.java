package com.risen.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.risen.common.pojo.EUITreeNode;
import com.risen.mapper.TbContentCategoryMapper;
import com.risen.pojo.TbContentCategory;
import com.risen.pojo.TbContentCategoryExample;
import com.risen.pojo.TbContentCategoryExample.Criteria;
import com.risen.service.ContentCategorySerivice;

/**
 * 内容分类管理 服务
 * @author sen
 *
 */
@Service
public class ContentCategorySeriviceImpl implements ContentCategorySerivice {
	
	@Resource
	private TbContentCategoryMapper contentCategoryMapper;
	
	/**
	 * 获取内容分类的列表
	 */
	@Override
	public List<EUITreeNode> getContentCatList(long parentId) {
		//根据parentId查询节点列表
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//转化数据===>List<EUITreeNode>
		List<EUITreeNode> resultList=new ArrayList<EUITreeNode>();
		for (TbContentCategory contentCat : list) {
			//创建一个节点
			EUITreeNode node =new EUITreeNode();
			node.setId(contentCat.getId());
			node.setText(contentCat.getName());
			node.setState(contentCat.getIsParent()?"closed":"open");
			
			resultList.add(node);
			
		}
		return resultList;
	}

}
