package com.threefriend.lightspace.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.ClassesServiceImpl;
import com.threefriend.lightspace.service.Impl.SortServiceImpl;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 排座控制器
 * @author Administrator
 *
 */
@RestController
public class SortController {
	
	@Autowired
	private SortServiceImpl sort_impl;
	
	
	/**
	 * 排座
	 * @param params
	 * @return
	 */
	@PostMapping("/sortList")
	@ResponseBody
	public ResultVO sortList(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(sort_impl.studentSort(Integer.valueOf(params.get("classId")),Integer.valueOf(params.get("type"))));
	}
	

}