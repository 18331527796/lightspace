package com.threefriend.lightspace.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
		return ResultVOUtil.success(record_impl.deleteRecord(Integer.valueOf(params.get("id")),params.get("token")));
	}
	
	/**
	 * 基础数据列表
	 * @param params
	 * @return
	 */
	@PostMapping("/recordList")
	@ResponseBody
	public ResultVO recordList(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.recordList(params.get("token")));
	}
	
	/**
	 * 基础数据模糊查询
	 * @param params
	 * @return
	 */
	@PostMapping("/queryRecord")
	@ResponseBody
	public ResultVO queryRecord(@RequestParam Map<String, String> params) {
		return record_impl.findByName(params.get("name"));
	}
	
	/**
	 * 按照学生id查询最新的基础数据
	 * @param params
	 * @return
	 */
	@PostMapping("/studentRecord")
	@ResponseBody
	public ResultVO studentRecord(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.findByStudentId(Integer.valueOf(params.get("id"))));
	}
	
	/**
	 * 按照学生id查询所有的基础数据
	 * @param params
	 * @return
	 */
	@PostMapping("/studentRecords")
	@ResponseBody
	public ResultVO studentRecords(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.findAllByStudentId(Integer.valueOf(params.get("studentId")),Long.valueOf(params.get("time"))));
	}
	
	/**
	 * 根据学校 进行数据分析
	 * @param params
	 * @return
	 */
	@PostMapping("/schoolStatistics")
	@ResponseBody
	public ResultVO schoolStatistics(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.schoolStatisticsOld(Integer.valueOf(params.get("schoolId"))));
	}
	
	/**
	 * 根据班级  进行数据分析
	 * @param params
	 * @return
	 */
	@PostMapping("/classStatistics")
	@ResponseBody
	public ResultVO classStatistics(@RequestParam Map<String, String> params) {
		return ResultVOUtil.success(record_impl.classStatisticsOld(Integer.valueOf(params.get("classId"))));
	}
	
	/**
	 * 批量导入检测数据
	 * @param file
	 * @param token
	 * @return
	 */
	@ResponseBody
	@PostMapping(value="/recordExcel")
	public ResultVO  upload(@RequestParam(value="file",required = false)MultipartFile file,@RequestParam(value="token")String token){
		return record_impl.readRecordExcel(file,token);
	}
	
	/**
	 * 下载模板（流方式）（暂停使用）
	 * @param res
	 */
	@ResponseBody
	@PostMapping(value="/downloadRecord")
	public void  downloadStudent(HttpServletResponse res){
		record_impl.download(res);
	}
}
