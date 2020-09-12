package com.threefriend.schoolclient.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.schoolclient.service.SchoolCreateQrcoreService;
import com.threefriend.schoolclient.service.SchoolStudentService;

@RestController
@RequestMapping("/school")
public class SchoolStudentController {

	@Autowired
	private SchoolStudentService student_impl;
	@Autowired
	private SchoolCreateQrcoreService cq_impl;
	
	@PostMapping("/getStudent")
	public ResultVO getStudent(@RequestParam Map<String, String> params,HttpSession session) {
		return student_impl.getAllStudent(params, session);
	}
	
	@PostMapping("/getStudentByClass")
	public ResultVO getStudentByClass(@RequestParam Map<String, String> params) {
		return student_impl.getStudentByClass(params);
	}
	
	@PostMapping("/addStudent")
	public ResultVO addStudent(@RequestParam Map<String, String> params) {
		return student_impl.addStudent(params);
	}
	
	@PostMapping("/editStudent")
	public ResultVO editStudent(@RequestParam Map<String, String> params) {
		return student_impl.editStudent(params);
	}
	
	@PostMapping("/saveStudent")
	public ResultVO saveStudent(@RequestParam Map<String, String> params) {
		return student_impl.saveStudent(params);
	}
	
	@PostMapping("/deleteStudent")
	public ResultVO deleteStudent(@RequestParam Map<String, String> params) {
		return student_impl.deleteStudent(params);
	}
	
	@PostMapping("/getAllWord")
	public ResultVO getAllWord(@RequestParam Map<String, String> params,HttpSession session) {
		return student_impl.getAllWord(params,session);
	}
	
	@PostMapping("/getStudentWord")
	public ResultVO getStudentWord(@RequestParam Map<String, String> params) {
		return student_impl.findAllByStudentId(params);
	}
	
	/**
	 * 批量导入学生信息
	 * @param file
	 * @return
	 */
	@PostMapping(value="/studentExcel")
	public ResultVO  upload(@RequestParam(value="file",required = false)MultipartFile file){
		return student_impl.readStudentExcel(file);
	}
	
	@PostMapping("/download")
	public ResultVO download(@RequestParam Map<String, String> params,HttpServletResponse response ,HttpSession session) {
		return cq_impl.download(response,params,session);
	}
}
