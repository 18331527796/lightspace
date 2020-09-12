package com.threefriend.schoolclient.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.schoolclient.service.SchoolRecordService;

@RestController
@RequestMapping("/school")
public class SchoolRecordController {

	@Autowired
	private SchoolRecordService record_dao;
	
	@PostMapping("/screeningList")
	public ResultVO screeningList(@RequestParam Map<String, String> params , HttpSession session) {
		return record_dao.screeningList(params, session);
	}
	
	@PostMapping("/screeningWearList")
	public ResultVO screeningWearList(@RequestParam Map<String, String> params , HttpSession session) {
		return record_dao.screeningWearList(params, session);
	}
	
	@PostMapping("/screeningByStudentId")
	public ResultVO screeningByStudentId(@RequestParam Map<String, String> params ) {
		return record_dao.screeningByStudentId(params);
	}
	
	@PostMapping("/screeningWearByStudentId")
	public ResultVO screeningWearByStudentId(@RequestParam Map<String, String> params ) {
		return record_dao.screeningWearByStudentId(params);
	}
	
	@PostMapping("/deleteScreening")
	public ResultVO deleteScreening(@RequestParam Map<String, String> params ) {
		return record_dao.deleteScreening(params);
	}
	
	@PostMapping("/deleteScreeningWear")
	public ResultVO deleteScreeningWear(@RequestParam Map<String, String> params ) {
		return record_dao.deleteScreeningWear(params);
	}
	
	@PostMapping("/recordSurvey")
	public ResultVO recordSurvey(@RequestParam Map<String, String> params ,HttpSession session) {
		return record_dao.recordSurvey(params,session);
	}
	
	@PostMapping("/recordVisionGrade")
	public ResultVO RecordVisionGrade(@RequestParam Map<String, String> params ,HttpSession session) {
		return record_dao.RecordVisionGrade(params,session);
	}
	
	@PostMapping("/ViewGradeReport")
	public ResultVO ViewGradeReport(@RequestParam Map<String, String> params ,HttpSession session) {
		return record_dao.ViewGradeReport(params,session);
	}
	
	@PostMapping("/ViewClassReport")
	public ResultVO ViewClassReport(@RequestParam Map<String, String> params ,HttpSession session) {
		return record_dao.ViewClassReport(params,session);
	}
	
	@PostMapping("/pushReport")
	public ResultVO pushReport(@RequestParam Map<String, String> params ,HttpSession session) {
		return record_dao.pushReport(params,session);
	}
}
