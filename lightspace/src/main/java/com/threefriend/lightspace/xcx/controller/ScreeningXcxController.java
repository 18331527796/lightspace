package com.threefriend.lightspace.xcx.controller;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.ScreeningXcxService;

/**
 * 筛查控制层
 *
 */
@RestController
@RequestMapping("/xcx")
public class ScreeningXcxController {

	@Autowired
	private ScreeningXcxService screening_impl;
	
	/**
	 * 三级联动
	 * @return
	 */
	
	@PostMapping("/cascade")
	public ResultVO selectStudent() {
		return screening_impl.selectStudent();
	}
	
	
	/**
	 * 新增筛选记录
	 * @param params
	 * @return
	 * @throws ParseException 
	 */
	
	@PostMapping("/addScreening")
	public ResultVO addScreening(@RequestParam Map<String, String> params) throws ParseException {
		return screening_impl.addScreening(params);
	}
	
	/**
	 * 新增筛选记录(戴镜)
	 * @param params
	 * @return
	 * @throws ParseException 
	 */
	
	@PostMapping("/addWearScreening")
	public ResultVO addWearScreening(@RequestParam Map<String, String> params) throws ParseException {
		return screening_impl.addScreeningWear(params);
	}
	
	/**
	 * 所有视标返回数据
	 * @return
	 */
	
	@PostMapping("/optotype")
	public ResultVO optotype() {
		return screening_impl.selectOptotype();
	}
	
	/**
	 * 按照id查找筛选档案
	 * @param params
	 * @return
	 */
	
	@PostMapping("/findScreening")
	public ResultVO findScreening(@RequestParam Map<String, String> params) {
		return screening_impl.findById(params);
	}
	
	/**
	 * 按照id查找筛选档案(戴镜)
	 * @param params
	 * @return
	 */
	
	@PostMapping("/findWearScreening")
	public ResultVO findWearScreening(@RequestParam Map<String, String> params) {
		return screening_impl.findWearById(params);
	}
	//↓↓↓↓↓↓↓↓新写的↓↓↓↓↓↓↓↓
	
	/**
	 * 档案首页
	 * @param params
	 * @return
	 */
	
	@PostMapping("/screeningList")
	public ResultVO allChildrenScreening(@RequestParam Map<String, String> params) {
		return screening_impl.allChildrenScreening(params);
	}
	
	@PostMapping("/deleteScreening")
	public ResultVO deleteScreening(@RequestParam Map<String, String> params) {
		return screening_impl.deleteScreening(params);
	}
}
