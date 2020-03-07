package com.threefriend.lightspace.xcx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.PartnershipServiceImpl;

@RestController
@RequestMapping("/xcx")
public class PartnershipController {
	@Autowired
	private PartnershipServiceImpl partnership_Impl;

	
	@ResponseBody
	@PostMapping("/partnershipList")
	public ResultVO selectStudent() {
		return partnership_Impl.partnershipList();
	}
}
