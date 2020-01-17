package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.RecordServiceImpl;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class RecordController {

	@Autowired
	private RecordServiceImpl record_impl;
	
	/**
	 * 添加基础数据
	 * @param params
	 * @return
	 */
	@PostMapping("/addRecord")
	@ResponseBody
	public ResultVO addRecord(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.addRecord(params));
	}
	
	/**
	 * 修改基础数据
	 * @param params
	 * @return
	 */
	@PostMapping("/editRecord")
	@ResponseBody
	public ResultVO editRecord(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.editRecord(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 修改后保存
	 * @param params
	 * @return
	 */
	@PostMapping("/saveRecord")
	@ResponseBody
	public ResultVO saveRecord(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.saveRecord(params));
	}
	
	/**
	 * 删除
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteRecord")
	@ResponseBody
	public ResultVO deleteRecord(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.deleteRecord(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 基础数据列表
	 * @param params
	 * @return
	 */
	@PostMapping("/recordList")
	@ResponseBody
	public ResultVO recordList() {
		return ResultVOUtil.success(record_impl.recordList());
	}
	
	/**
	 * 基础数据模糊查询
	 * @param params
	 * @return
	 */
	@PostMapping("/queryRecord")
	@ResponseBody
	public ResultVO queryRecord(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.findByName(params.get("name")));
	}
	
	
}
