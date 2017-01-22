package com.risen.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.risen.common.utils.FtpUtil;
import com.risen.common.utils.IDUtil;
import com.risen.service.PictureService;
/**
 * 图片上传service
 * @author sen
 *
 */
@Service
public class PictureServiceImpl implements PictureService {
	
	
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		//生成新的文件名
		
		//取原文件名,用以获取图片扩展名 
		String oldName=uploadFile.getOriginalFilename();
		
		//生成新文件名
		String newName=IDUtil.genImageName();
		newName=newName+oldName.substring(oldName.lastIndexOf("."));
		
		//图片保存路径
		String imagePath=new DateTime().toString("/yyyy/MM/dd");
		//结果map
		Map resultMap=new HashMap<>();
		try {
			//图片上传
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, 
					FTP_BASE_PATH, imagePath, newName, uploadFile.getInputStream());
			if(!result){
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
			return resultMap;
			
		} catch (IOException e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}
