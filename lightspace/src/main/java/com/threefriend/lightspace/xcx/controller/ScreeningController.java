package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.ScreeningService;

/**
 * 筛查控制层
 *
 */
@RestController
@RequestMapping("/xcx")
public class ScreeningController {

	@Autowired
	private ScreeningService screening_impl;
	
	/**
	 * 三级联动
	 * @return
	 */
	@ResponseBody
	@PostMapping("/cascade")
	public ResultVO selectStudent() {
		return screening_impl.selectStudent();
	}
	
	
	/**
	 * 新增筛选记录
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/addScreening")
	public ResultVO addScreening(@RequestParam Map<String, String> params) {
		return screening_impl.addScreening(params);
	}
	
	/**
	 * 所有视标返回数据
	 * @return
	 */
	@ResponseBody
	@PostMapping("/optotype")
	public ResultVO optotype() {
		return screening_impl.selectOptotype();
	}
	
	/**
	 * 按照id查找筛选档案
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/findScreening")
	public ResultVO findScreening(@RequestParam Map<String, String> params) {
		return screening_impl.findById(params);
	}
	//↓↓↓↓↓↓↓↓新写的↓↓↓↓↓↓↓↓
	
	/**
	 * 档案首页
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/screeningList")
	public ResultVO allChildrenScreening(@RequestParam Map<String, String> params) {
		return screening_impl.allChildrenScreening(params);
	}
}
