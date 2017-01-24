package com.risen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.risen.common.pojo.EUITreeNode;
import com.risen.common.utils.Result;
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
	
	/**
	 * 新增内容分类
	 */
	@Override
	public Result insertContentCategory(long parentId, String name) {
		//创建pojo
		TbContentCategory cat=new TbContentCategory();
		//设置属性值
		cat.setCreated(new Date());
		cat.setUpdated(new Date());
		cat.setIsParent(false);
		cat.setName(name);
		cat.setParentId(parentId);
		cat.setStatus(1);
		cat.setSortOrder(1);
		//执行添加
		contentCategoryMapper.insert(cat);
		
		//查看父节点的isParent是否为true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCat.getIsParent()){
			//更新父节点
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		
		
		return Result.ok(cat);
	}

	 /**
	  * 删除内容分类 
	  */
	@Override
	public Result deleteContentCategory(long id) {
		//根据id查询记录
		TbContentCategory theCat = contentCategoryMapper.selectByPrimaryKey(id);
		//获取parentId
		long parentId=theCat.getParentId();
		//根据id删除记录
		contentCategoryMapper.deleteByPrimaryKey(id);
		//根据parentId查询记录
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//如果list为空，说明id为parentId的记录已经没有子节点了，将其isParent属性设置为false
		if(list == null || list.size()==0){
			//修改哪条记录
			TbContentCategoryExample example2=new TbContentCategoryExample();
			Criteria criteria2 = example2.createCriteria();
			criteria2.andIdEqualTo(parentId);
			//需要修改的字段
			TbContentCategory cat=new TbContentCategory();
			cat.setIsParent(false);
			//执行修改
			contentCategoryMapper.updateByExampleSelective(cat, example2);
		}
		return Result.ok();
	}
	
	
	/**
	 * 重命名内容分类
	 */
	@Override
	public void renameContentCategory(long id, String name) {
		//修改哪条记录
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		//设置要修改的字段
		TbContentCategory cat=new TbContentCategory();
		cat.setName(name);
		//执行修改
		contentCategoryMapper.updateByExampleSelective(cat, example);
	}
	
	

}
