package com.risen.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
	
	@SuppressWarnings("rawtypes")
	Map uploadPicture(MultipartFile uploadFile);
}
