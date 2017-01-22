package com.risen.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.risen.common.utils.JsonUtil;
import com.risen.service.PictureService;

/**
 * 图片上传处理
 * @author sen
 *
 */
@Controller
public class PictureController {
	
	@Resource
	private PictureService pictureService;
	
	/**
	 * 图片上传
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping("pic/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile){
		Map resultMap=pictureService.uploadPicture(uploadFile);
		//为了保证浏览器兼容性，需要把结果转化成json格式字符串
		String json=JsonUtil.objectToJson(resultMap);
		return json;
	}
	
}
