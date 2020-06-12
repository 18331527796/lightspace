package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.CreateQrcoreServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.CreateQrcoreXcxServiceImpl;

/**
 *	生成孩子二维码
 */
@RestController
@RequestMapping("/xcx")
public class CreateQrcoreXcxController {

	@Autowired
	private CreateQrcoreServiceImpl createQrcore_impl;
	@Autowired
	private CreateQrcoreXcxServiceImpl createQrcoreXcx_impl;
	
	@PostMapping("/download")
	public ResultVO getCreateQrcore(HttpServletResponse response ,@RequestParam Map<String, String> params) {
		return createQrcore_impl.downloadXcx(response, params);
	}
	
	@PostMapping("/allSchool")
	public ResultVO allSchool() {
		return createQrcoreXcx_impl.allSchool();
	}
	
	@PostMapping("/allClass")
	public ResultVO classByGradeAndSchool(@RequestParam Map<String, String> params) {
		return createQrcoreXcx_impl.classByGradeAndSchool( params);
	}
	
	@PostMapping("/allStudent")
	public ResultVO studentByClassId(@RequestParam Map<String, String> params) {
		return createQrcoreXcx_impl.studentByClassId(params);
	}
	
	@PostMapping("/chkStudent")
	public ResultVO chkStudent(@RequestParam Map<String, String> params) {
		return createQrcoreXcx_impl.chkStudent(params);
	}
	
	
}
