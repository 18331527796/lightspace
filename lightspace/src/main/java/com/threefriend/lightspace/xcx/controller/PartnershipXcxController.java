package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.xcx.PartnershipXcxServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	合作机构控制器
 */
@RestController
@RequestMapping("/xcx")
public class PartnershipXcxController {
	@Autowired
	private PartnershipXcxServiceImpl partnership_Impl;

	
	
	@PostMapping("/partnershipList")
	public ResultVO selectStudent() {
		return partnership_Impl.partnershipList();
	}
	
}
