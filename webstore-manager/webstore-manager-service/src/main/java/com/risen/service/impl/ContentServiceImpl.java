package com.risen.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risen.common.pojo.EUIDataGridResult;
import com.risen.common.utils.Result;
import com.risen.mapper.TbContentMapper;
import com.risen.pojo.TbContent;
import com.risen.pojo.TbContentExample;
import com.risen.pojo.TbContentExample.Criteria;
import com.risen.service.ContentService;

/**
 * 内容管理
 * @author sen
 *
 */
@Service
public class ContentServiceImpl implements ContentService{
	
	@Resource
	private TbContentMapper contentMapper;
	
	/**
	 * 根据内容分类id查询内容列表
	 */
	@Override
	public EUIDataGridResult getContentList(int page, int rows, long categoryId) {
		//设置查询条件
		TbContentExample example=new TbContentExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andCategoryIdEqualTo(categoryId);
		//分页处理
		PageHelper.startPage(page, rows);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//返回结果处理
		EUIDataGridResult result=new EUIDataGridResult();
		result.setRows(list);
		//总记录数
		PageInfo<TbContent> pageInfo=new PageInfo<TbContent>(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}
	
	/**
	 * 新增内容
	 */
	@Override
	public Result insertContent(TbContent content) {
		//补全参数
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//执行添加
		contentMapper.insert(content);
		
		return Result.ok();
	}
	

}
