package com.risen.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risen.common.pojo.EUIDataGridResult;
import com.risen.common.utils.Result;
import com.risen.mapper.TbItemParamMapper;
import com.risen.pojo.TbItemParam;
import com.risen.pojo.TbItemParamExample;
import com.risen.pojo.TbItemParamExample.Criteria;
import com.risen.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService{
	
	@Resource
	private TbItemParamMapper itemParamMapper;
	
	/**
	 * 获取规格参数模板列表
	 */
	@Override
	public EUIDataGridResult getItemParamList(int page, int rows) {
		
				//创建查询条件对象
				TbItemParamExample example =new TbItemParamExample();
				//分页处理
				PageHelper.startPage(page, rows);
				//执行查询
				List<TbItemParam> list = itemParamMapper.selectByExample(example);
				
				//EasyUI的结果对象
				EUIDataGridResult result=new EUIDataGridResult();
				//设置属性
				result.setRows(list);
				//获取记录总数
				PageInfo<TbItemParam> pageInfo=new PageInfo<TbItemParam>(list);
				result.setTotal(pageInfo.getTotal());
				//返回
				return result;
	}
	
	/**
	 * 根据商品分类查询规格参数模板
	 */
	@Override
	public Result getItemParamByCid(long cid) {
		
		//创建查询条件对象
		TbItemParamExample example =new TbItemParamExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		//查询
		List<TbItemParam> list = itemParamMapper.selectByExample(example);
		//判断
		if(list!=null && list.size()>0){
			return Result.ok(list.get(0));
		}
		return Result.ok();
	}

}
