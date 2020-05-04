package com.threefriend.lightspace.xcx.service;


import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

public interface PartnershipXcxService {

	//合作机构列表
	ResultVO partnershipList();
	
	
}
