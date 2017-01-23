package com.risen.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.risen.mapper.TbItemCatMapper;
import com.risen.pojo.TbItemCat;
import com.risen.pojo.TbItemCatExample;
import com.risen.pojo.TbItemCatExample.Criteria;
import com.risen.rest.pojo.CatNode;
import com.risen.rest.pojo.CatResult;
import com.risen.rest.service.ItemCatService;

/**
 * 商品分类服务
 * @author sen
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Resource
	private TbItemCatMapper itemCatMapper;
	
	/**
	 * 获取商品所有分类 的pojo包装对象
	 */
	@Override
	public CatResult getItemCatList() {
		
		CatResult catResult=new CatResult();
		//查询分类列表
		catResult.setData(getCatList(0));
		
		return catResult;
	}
	
	/**
	 * 查询分类列表
	 */
	public List<?> getCatList(long parentId){
		//创建查询条件
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//返回值 list
		List resultList=new ArrayList<>();
		
		//循环计数器
		int count=0;
		//向resultList中添加节点
		for(TbItemCat tbItemCat:list){
			//判断是否为父节点
			if(tbItemCat.getIsParent()){
				CatNode catNode=new CatNode();
				if(parentId==0){
					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				}else{
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				catNode.setItems(getCatList(tbItemCat.getId()));
				
				resultList.add(catNode);
				count++;
				
				//第一层只去14条记录
				if(parentId==0 && count >=14){
					break;
				}
				//如果是子节点
			}else{
				resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
			}
		}
		return resultList;
	}

}
