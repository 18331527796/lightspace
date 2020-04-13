package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.xcx.service.Impl.XcxCreateQrcoreServiceImpl;

/**
 *	生成孩子二维码
 */
@RestController
@RequestMapping("/xcx")
public class XcxCreateQrcoreController {

	@Autowired
	private XcxCreateQrcoreServiceImpl createQrcore_impl;
	
	@PostMapping("/download")
	public void getCreateQrcore(HttpServletResponse response ,@RequestParam Map<String, String> params) {
		createQrcore_impl.download(response, params);
	}
}
