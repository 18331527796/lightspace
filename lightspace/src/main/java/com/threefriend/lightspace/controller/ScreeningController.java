package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.ScreeningServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class ScreeningController {

	@Autowired
	private ScreeningServiceImpl screening_impl;
	
	@PostMapping("screeningList")
	public ResultVO screeningList(@RequestParam Map<String, String> params) {
		return screening_impl.screeningList(params);
	}
	
	@PostMapping("deleteScreening")
	public ResultVO deleteScreening(@RequestParam Map<String, String> params) {
		return screening_impl.deleteScreening(params);
	}
	
	@PostMapping("screeningWearList")
	public ResultVO screeningWearList(@RequestParam Map<String, String> params) {
		return screening_impl.screeningWearList(params);
	}
	
	@PostMapping("deleteScreeningWear")
	public ResultVO deleteScreeningWear(@RequestParam Map<String, String> params) {
		return screening_impl.deleteScreeningWear(params);
	}
	
	@PostMapping("screeningExcel")
	public ResultVO screeningExcel(@RequestParam Map<String, String> params) {
		return screening_impl.screeningExcel(params);
	}
	
	@PostMapping("screeningWearExcel")
	public ResultVO screeningWearExcel(@RequestParam Map<String, String> params) {
		return screening_impl.screeningWearExcel(params);
	}
	
	@PostMapping("screeningStudentExcel")
	public ResultVO studentExcel(@RequestParam Map<String, String> params) {
		return screening_impl.screeningStudentExcel(params);
	}
	
	@PostMapping("screeningWearStudentExcel")
	public ResultVO studentWearExcel(@RequestParam Map<String, String> params) {
		return screening_impl.screeningWearStudentExcel(params);
	}
	
	@PostMapping("screeningByStudent")
	public ResultVO screeningByStudent(@RequestParam Map<String, String> params) {
		return screening_impl.screeningByStudent(params);
	}
	
	@PostMapping("screeningWearByStudent")
	public ResultVO screeningWearByStudent(@RequestParam Map<String, String> params) {
		return screening_impl.screeningWearByStudent(params);
	}
}
