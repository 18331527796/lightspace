package com.threefriend.lightspace.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.service.Impl.PartnershipServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class PartnershipController {

	@Autowired
	private PartnershipServiceImpl partnership_impl;
	
	@PostMapping("/partnershipList")
	public ResultVO partnershipList(@RequestParam Map<String, String> params,HttpSession session) {
		return partnership_impl.partnershipList(params,session);
	}
	
	@PostMapping("/addPartnership")
	public ResultVO addPartnership(@RequestParam(value="file",required=false) MultipartFile file,
			@RequestParam Map<String, String> params,
			HttpServletRequest request,
			HttpSession session) {
		return partnership_impl.addPartnership(file, params,request,session);
	}
	
	@PostMapping("/deletePartnership")
	public ResultVO deletePartnership(@RequestParam Map<String, String> params) {
		return partnership_impl.deletePartnership(params);
	}
	
	
	
}
