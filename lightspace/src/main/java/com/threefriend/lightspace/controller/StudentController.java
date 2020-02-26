package com.threefriend.lightspace.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.service.Impl.StudentServiceImpl;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class StudentController {

	@Autowired
	private StudentServiceImpl student_Impl;
	
	
	/**
	 * 学生列表
	 * @param params
	 * @return
	 */
	@PostMapping("/studentList")
	@ResponseBody
	public ResultVO studentList(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(student_Impl.studentList(params));
	}
	
	/**
	 * 添加学生
	 * @param params
	 * @return
	 */
	@PostMapping("/addStudent")
	@ResponseBody
	public ResultVO insertStudent(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(student_Impl.addStudent(params));
	}
	
	/**
	 * 修改
	 * @param params
	 * @return
	 */
	@PostMapping("/editStudent")
	@ResponseBody
	public ResultVO editStudent(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(student_Impl.findById(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 修改后保存
	 * @param params
	 * @return
	 */
	@PostMapping("/saveStudent")
	@ResponseBody
	public ResultVO saveStudent(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(student_Impl.saveStudent(params));
	}
	
	/**
	 * 删除
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteStudent")
	@ResponseBody
	public ResultVO deleteStudent(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(student_Impl.deleteStudent(Integer.valueOf(params.get("id")),params.get("token")));
	}
	
	/**
	 * 按照学校 班级 找学生
	 * @param params
	 * @return
	 */
	@PostMapping("/queryStudentBySidCid")
	@ResponseBody
	public ResultVO queryStudentsBySidCid(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(student_Impl.queryBySidCid(Integer.valueOf(params.get("schoolId")), Integer.valueOf(params.get("classId"))));
	}
	
	/**
	 * 模糊查询
	 * @param params
	 * @return
	 */
	@PostMapping("/queryStudent")
	@ResponseBody
	public ResultVO queryStudents(@RequestParam Map<String, String> params) {
		return student_Impl.findByNameLike(params.get("name"));
	}
	
	/**
	 * 按照学校班级姓名模糊查询
	 * @param params
	 * @return
	 */
	@PostMapping("/searchStudents")
	@ResponseBody
	public ResultVO searchStudents(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(student_Impl.findBySchoolIdAndClassesIdAndNameLike(Integer.valueOf(params.get("schoolId")), Integer.valueOf(params.get("classId")), params.get("name")));
	}
	
	/**
	 * 批量导入学生信息
	 * @param file
	 * @param token
	 * @return
	 */
	@ResponseBody
	@PostMapping(value="/studentExcel")
	public ResultVO  upload(@RequestParam(value="file",required = false)MultipartFile file,@RequestParam(value="token")String token){
		return student_Impl.readStudentExcel(file,token);
	}
	
	/**
	 * 下载模板（流方式）（暂停使用）
	 * @param res
	 */
	@ResponseBody
	@RequestMapping(value="/downloadStudent")
	public void  downloadStudent(HttpServletResponse res){
		student_Impl.download(res);
	}
}
