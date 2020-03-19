package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.SchoolServiceImpl;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 学校控制器
 * @author 八爪
 *
 */
@RestController
public class SchoolController {
	@Autowired
	private SchoolServiceImpl school_Impl;
	
	
	/**
	 * 添加学校
	 * @param params
	 * @return
	 */
	@PostMapping("/addSchool")
	
	public ResultVO insertSchool(@RequestParam Map<String, String> params) {
		return school_Impl.addSchool(params);
	}
	
	/**
	 * 学校列表
	 * @return
	 */
	@PostMapping("/schoolList")
	
	public ResultVO findAllSchool(@RequestParam("token") String token) {
		return ResultVOUtil.success(school_Impl.findAllSchool(token));
	}
	
	/**
	 * 修改学校信息
	 * @param params
	 * @return
	 */
	@PostMapping("/editSchool")
	
	public ResultVO editSchool(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(school_Impl.findSchoolById(params));
	}
	
	/**
	 * 保存修改学校信息
	 * @param params
	 * @return
	 */
	@PostMapping("/saveSchool")
	
	public ResultVO saveSchool(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(school_Impl.alterSchool(params));
	}
	
	/**
	 * 删除学校
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteSchool")
	
	public ResultVO deleteSchool(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(school_Impl.deleteSchool(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 模糊查询学校
	 * @param params
	 * @return
	 */
	@PostMapping("/querySchool")
	
	public ResultVO querySchool(@RequestParam Map<String, String> params) {
		return school_Impl.findAllSchoolLike(params.get("name"));
	}
	
	

}
