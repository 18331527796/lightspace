package com.threefriend.schoolclient.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.schoolclient.service.SchoolUserService;

@Controller
@RequestMapping("/school")
public class SchoolUserController {

	@Autowired
	private SchoolUserService user_service;
	
	@GetMapping("")
	public String school() {
		return "/school.html";
	}
	
	@ResponseBody
	@PostMapping("/login")
	public ResultVO login(@RequestParam Map<String, String> params , HttpSession session) {
		return user_service.login(params, session);
	}
	
	@ResponseBody
	@PostMapping("/regionLogin")
	public ResultVO regionLogin(@RequestParam Map<String, String> params , HttpSession session) {
		return user_service.regionLogin(params, session);
	}
	
	@ResponseBody
	@PostMapping("/survey")
	public ResultVO survey( HttpSession session) {
		return user_service.survey( session);
	}
	@ResponseBody
	@PostMapping("/badPercentage")
	public ResultVO badPercentage( HttpSession session) {
		return user_service.badPercentage( session);
	}
	@ResponseBody
	@PostMapping("/visionGrade")
	public ResultVO visionGrade( HttpSession session) {
		return user_service.visionGrade( session);
	}
	@ResponseBody
	@PostMapping("/brokenLine")
	public ResultVO brokenLine( HttpSession session) {
		return user_service.brokenLine( session);
	}
	
	@ResponseBody
	@PostMapping("/top5")
	public ResultVO top5(@RequestParam Map<String, String> params , HttpSession session) {
		return user_service.top5(params,session);
	}
	
	@ResponseBody
	@PostMapping("/getAllClass")
	public ResultVO getAllClass( HttpSession session) {
		return user_service.getAllClass(session);
	}
	
	@ResponseBody
	@PostMapping("/getAllSemester")
	public ResultVO getAllSemester( HttpSession session) {
		return user_service.getAllSemester(session);
	}
	
	@ResponseBody
	@PostMapping("/undetectedList")
	public ResultVO undetectedList( HttpSession session) {
		return user_service.undetectedList(session);
	}
	
	@ResponseBody
	@PostMapping("/badList")
	public ResultVO badList( HttpSession session) {
		return user_service.badList(session);
	}
	
	@ResponseBody
	@PostMapping("/loginOut")
	public ResultVO loginOut( HttpSession session) {
		return user_service.loginOut(session);
	}
	
	@ResponseBody
	@PostMapping("/getUserSchools")
	public ResultVO getUserSchools( HttpSession session) {
		return user_service.getUserSchools(session);
	}
	
	@ResponseBody
	@PostMapping("/getRegionSchools")
	public ResultVO getRegionSchools( HttpSession session) {
		return user_service.getRegionSchools(session);
	}
	
	@ResponseBody
	@PostMapping("/changeSession")
	public ResultVO changeSession(@RequestParam Map<String, String> params , HttpSession session) {
		return user_service.changeSession(params,session);
	}
}
