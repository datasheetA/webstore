package com.risen.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.risen.common.utils.HttpClientUtil;
import com.risen.common.utils.JsonUtil;
import com.risen.common.utils.Result;
import com.risen.pojo.TbItem;
import com.risen.pojo.TbItemDesc;
import com.risen.pojo.TbItemParamItem;
import com.risen.portal.pojo.ItemInfo;
import com.risen.portal.service.ItemService;


/**
 * 商品详情页面处理 service层
 * @author sen
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	
	@Value("${ITEM_BASE_URL}")
	private String ITEM_BASE_URL;
	
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
	/**
	 * 取商品基本信息
	 */
	@Override
	public ItemInfo getItemBaseInfo(long itemId) {
		//调用rest的服务查询商品的基本信息
		try {
			String json = HttpClientUtil.doGet(ITEM_BASE_URL + itemId);
			if(!StringUtils.isBlank(json)){
				//转成Result对象（json位Result包装TbItem的字符串）
				Result result = Result.formatToPojo(json, ItemInfo.class);
				if(result.getStatus()==200){
					//取出TbItem对象
					ItemInfo item=(ItemInfo) result.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取商品描述
	 */
	@Override
	public String getItemDesc(long itemId) {
		// 调用rest的服务获取商品描述信息
		try {
			String json = HttpClientUtil.doGet(ITEM_DESC_URL + itemId);
			if(!StringUtils.isBlank(json)){
				//将json转成Result对象（json为Result包装了TbItemDesc）
				Result result = Result.formatToPojo(json, TbItemDesc.class);
				//取出TbItemDesc对象
				if(result.getStatus() == 200 ){
					TbItemDesc itemDesc=(TbItemDesc) result.getData();
					//取出商品描述
					String desc = itemDesc.getItemDesc();
					return desc;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 从rest的服务中获取商品规格参数
	 */
	@Override
	public String getItemParam(long itemId) {
		
		//调用服务
		try {
			String json = HttpClientUtil.doGet(ITEM_PARAM_URL + itemId);
			if(!StringUtils.isBlank(json)){
				//json转成Result对象
				Result result = Result.formatToPojo(json, TbItemParamItem.class);
				if(result.getStatus() == 200 ){
					TbItemParamItem paramItem=(TbItemParamItem) result.getData();
					String paramData=paramItem.getParamData();
					/*
					 * 将paramData(json)转换成html片段
					 */
					List<Map> jsonList = JsonUtil.jsonToList(paramData, Map.class);
					StringBuffer sb = new StringBuffer();
					sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
					sb.append("    <tbody>\n");
					for(Map m1:jsonList) {
						sb.append("        <tr>\n");
						sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
						sb.append("        </tr>\n");
						List<Map> list2 = (List<Map>) m1.get("params");
						for(Map m2:list2) {
							sb.append("        <tr>\n");
							sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
							sb.append("            <td>"+m2.get("v")+"</td>\n");
							sb.append("        </tr>\n");
						}
					}
					sb.append("    </tbody>\n");
					sb.append("</table>");
					//返回html片段
					return sb.toString();
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

}
