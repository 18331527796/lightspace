package com.threefriend.lightspace.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

/** 轮播图
 * @author Administrator
 *
 */
public interface RotationPicService {

	ResultVO RotationPicList(Map<String, String> params);
	
	ResultVO addRotationPic(Map<String, String> params,MultipartFile pic);
	
	ResultVO editRotationPic(Map<String, String> params);
	
	ResultVO saveRotationPic(Map<String, String> params,MultipartFile pic);
	
	ResultVO deleteRotationPic(Map<String, String> params);
}
