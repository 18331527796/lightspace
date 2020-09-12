package com.threefriend.schoolclient.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.ParentServiceImpl;
import com.threefriend.lightspace.service.Impl.TeacherServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.schoolclient.service.impl.SchoolTeacherServiceImpl;

/**
 * 教师控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/school")
public class SchoolTeacherController {

	@Autowired
	private SchoolTeacherServiceImpl teacher_impl;
	
	/**
	 * 教师列表
	 * @param params
	 * @return
	 */
	
	@PostMapping("/teacherList")
	public ResultVO teacherList(@RequestParam Map<String, String> params,HttpSession session) {
		return teacher_impl.teacherList(params,session);
	}
	
	/**
	 * 新增教师
	 * @param params
	 * @return
	 */
	
	@PostMapping("/addTeacher")
	public ResultVO addTeacher(@RequestParam Map<String, String> params,HttpSession session) {
		return teacher_impl.addTeacher(params,session);
	}
	
	/**
	 * 修改
	 * @param params
	 * @return
	 */
	
	@PostMapping("/editTeacher")
	public ResultVO editTeacher(@RequestParam Map<String, String> params) {
		return teacher_impl.findById(params);
	}
	
	/**
	 * 修改后保存
	 * @param params
	 * @return
	 */
	
	@PostMapping("/saveTeacher")
	public ResultVO alertTeacher(@RequestParam Map<String, String> params) {
		return teacher_impl.alertTeacher(params);
	}
	
	/**
	 * 删除教师
	 * @param params
	 * @return
	 */
	
	@PostMapping("/deleteTeacher")
	public ResultVO deleteTeacher(@RequestParam Map<String, String> params) {
		return teacher_impl.deleteTeacher(params);
	}
	
	/**
	 * 搜索教师
	 * @param params
	 * @return
	 */
	
	@PostMapping("/queryTeacher")
	public ResultVO queryTeacher(@RequestParam Map<String, String> params,HttpSession session) {
		return teacher_impl.queryTeacher(params,session);
	}
	
	@PostMapping("/undetected")
	public ResultVO undetected(@RequestParam Map<String, String> params,HttpSession session) throws Exception {
		return teacher_impl.undetected(params,session);
	}
	
	@PostMapping("/untask")
	public ResultVO untask(@RequestParam Map<String, String> params,HttpSession session) throws Exception {
		return teacher_impl.untask(params,session);
	}
	
	@PostMapping("/decline")
	public ResultVO decline(@RequestParam Map<String, String> params,HttpSession session) throws Exception {
		return teacher_impl.decline(params,session);
	}
	
	@PostMapping("/remindUndetected")
	public ResultVO remindUndetected(@RequestParam("id") Integer[] params) throws Exception {
		return teacher_impl.remindUndetected(params);
	}
	
	@PostMapping("/remindDecline")
	public ResultVO remindDecline(@RequestParam("id") Integer[] params) throws Exception {
		return teacher_impl.remindDecline(params);
	}
	
	@PostMapping("/remindUntask")
	public ResultVO remindUntask(@RequestParam("id") Integer[] params) throws Exception {
		return teacher_impl.remindUntask(params);
	}
	
}
