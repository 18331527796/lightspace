package com.threefriend.lightspace.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.mapper.SchoolMapper;
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
	@ResponseBody
	public ResultVO insertSchool(@RequestParam Map<String, String> params) {
		school_Impl.addSchool(params);
		return ResultVOUtil.success();
	}
	
	/**
	 * 学校列表
	 * @return
	 */
	@PostMapping("/schoolList")
	@ResponseBody
	public ResultVO findAllSchool(@RequestParam("token") String token) {
		List<SchoolMapper> findAllSchool = school_Impl.findAllSchool(token);
		return ResultVOUtil.success(findAllSchool);
	}
	
	/**
	 * 修改学校信息
	 * @param params
	 * @return
	 */
	@PostMapping("/editSchool")
	@ResponseBody
	public ResultVO editSchool(@RequestParam Map<String, String> params) {
		SchoolMapper findSchoolById = school_Impl.findSchoolById(params);
		return ResultVOUtil.success(findSchoolById);
	}
	
	/**
	 * 保存修改学校信息
	 * @param params
	 * @return
	 */
	@PostMapping("/saveSchool")
	@ResponseBody
	public ResultVO saveSchool(@RequestParam Map<String, String> params) {
		List<SchoolMapper> alterSchool = school_Impl.alterSchool(params);
		return ResultVOUtil.success(alterSchool);
	}
	
	/**
	 * 删除学校
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteSchool")
	@ResponseBody
	public ResultVO deleteSchool(@RequestParam Map<String, String> params) {
		school_Impl.deleteSchool(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}
	
	/**
	 * 模糊查询学校
	 * @param params
	 * @return
	 */
	@PostMapping("/querySchool")
	@ResponseBody
	public ResultVO querySchool(@RequestParam Map<String, String> params) {
		List<SchoolMapper> findAllSchoolLike = school_Impl.findAllSchoolLike("%"+params.get("name")+"%");
		return ResultVOUtil.success(findAllSchoolLike);
	}
	
	

}
