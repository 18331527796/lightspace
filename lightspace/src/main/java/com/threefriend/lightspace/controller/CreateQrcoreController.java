package com.threefriend.lightspace.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.CreateQrcoreServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	生成孩子二维码
 */
@RestController
public class CreateQrcoreController {

	@Autowired
	private CreateQrcoreServiceImpl createQrcore_impl;
	
	@PostMapping("/download")
	public ResultVO getCreateQrcore(HttpServletResponse response ,@RequestParam Map<String, String> params) {
		return createQrcore_impl.download(response, params);
	}
}
