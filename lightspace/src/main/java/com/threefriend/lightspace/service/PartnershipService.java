package com.threefriend.lightspace.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

public interface PartnershipService {

	//合作机构列表
	ResultVO partnershipList();
	//新增合作机构
	ResultVO addPartnership(MultipartFile file,Map<String, String> params);
	//删除合作机构
	@Transactional
	ResultVO deletePartnership(Map<String, String> params);
	//uploadIMG
	String uploadImg(MultipartFile file);
}
