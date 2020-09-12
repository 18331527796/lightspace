package com.threefriend.schoolclient.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.SortServiceImpl;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 排座控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/school")
public class SchoolSortController {
	
	@Autowired
	private SortServiceImpl sort_impl;
	
	
	/**
	 * 排座
	 * @param params
	 * @return
	 */
	@PostMapping("/sortList")
	
	public ResultVO sortList(@RequestParam Map<String, String> params) {
		return sort_impl.studentSort(params);
	}
	
	/**
	 * 按照班级查询列表
	 * @param params
	 * @return
	 */
	@PostMapping("/classSorts")
	
	public ResultVO classSorts(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(sort_impl.byClassId(Integer.valueOf(params.get("classId"))));
	}
	
	/**
	 * 排座展示
	 * @param params
	 * @return
	 */
	@PostMapping("/showSort")
	
	public ResultVO showSort(@RequestParam Map<String, String> params) {
		return sort_impl.sortShow(Integer.valueOf(params.get("sortId")));
	}
	
	/**
	 * 排座拖拽后保存
	 * @param params
	 * @return
	 */
	@PostMapping("/saveSort")
	
	public ResultVO saveSort(@RequestParam Map<String, String> params) {
		return sort_impl.saveSort(params);
	}
	
	/**
	 * 调整排座
	 * @param params
	 * @return
	 */
	@PostMapping("/adjustSort")
	public ResultVO adjustSort(@RequestParam Map<String, String> params) {
		return sort_impl.adjustSort(params);
	}
	
	/**
	 * 验证排座
	 * @param params
	 * @return
	 */
	@PostMapping("/chkSort")
	public ResultVO chkSort(@RequestParam Map<String, String> params) {
		return sort_impl.chkSort(params);
	}

}
