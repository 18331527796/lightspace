package com.threefriend.schoolclient.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.schoolclient.service.impl.RegionClertServiceImpl;
import com.threefriend.schoolclient.service.impl.RegionPartnershipServiceImpl;
import com.threefriend.schoolclient.service.impl.RegionSchoolServiceImpl;
import com.threefriend.schoolclient.service.impl.RegionUserServiceImpl;


@Controller
@RequestMapping("/region")
public class RegionClientController {
	
	@Autowired
	private RegionPartnershipServiceImpl impl;
	@Autowired
	private RegionClertServiceImpl clert_impl;
	@Autowired
	private RegionSchoolServiceImpl school_impl;
	@Autowired
	private RegionUserServiceImpl user_impl;

	
	@GetMapping("")
	public String regionPage() {
		return "/region.html";
	}
	
	@ResponseBody
	@PostMapping("/getAllPartnership")
	public ResultVO getAllPartnership(HttpSession session) {
		return impl.getAllPartnership(session);
	}
	
	@ResponseBody
	@PostMapping("/partnershipList")
	public ResultVO partnershipList(@RequestParam Map<String, String> params,HttpSession session) {
		return impl.partnershipList(params,session);
	}
	
	@ResponseBody
	@PostMapping("/addPartnership")
	public ResultVO addPartnership(
			@RequestParam(value="file",required=false) MultipartFile file,
			@RequestParam Map<String, String> params,
			HttpServletRequest request,
			HttpSession session) {
		return impl.addPartnership(file, params, request, session);
	}
	
	@ResponseBody
	@PostMapping("/deletePartnership")
	public ResultVO deletePartnership(@RequestParam Map<String, String> params) {
		return impl.deletePartnership(params);
	}
	
	@ResponseBody
	@PostMapping("/clertList")
	public ResultVO clertList(@RequestParam Map<String, String> params,HttpSession session) {
		return clert_impl.clertList(params,session);
	}
	
	@ResponseBody
	@PostMapping("/addClert")
	public ResultVO addClert(@RequestParam Map<String, String> params) {
		return clert_impl.addClert(params);
	}
	
	
	@ResponseBody
	@PostMapping("/editClert")
	public ResultVO findById(@RequestParam Map<String, String> params) {
		return clert_impl.findById(params);
	}
	
	@ResponseBody
	@PostMapping("/saveClert")
	public ResultVO alertClert(@RequestParam Map<String, String> params) {
		return clert_impl.alertClert(params);
	}
	
	@ResponseBody
	@PostMapping("/deleteClert")
	public ResultVO deleteClert(@RequestParam Map<String, String> params) {
		return clert_impl.deleteClert(params);
	}
	
	@ResponseBody
	@PostMapping("/undetectedList")
	public ResultVO undetectedList(HttpSession session) {
		return impl.undetectedList(session);
	}
	
	@ResponseBody
	@PostMapping("/badList")
	public ResultVO badList(HttpSession session) {
		return impl.badList(session);
	}
	
	@ResponseBody
	@PostMapping("/addSchool")
	public ResultVO addSchool(@RequestParam Map<String, String> params ,HttpSession session) {
		return school_impl.addSchool(params,session);
	}
	@ResponseBody
	@PostMapping("/editSchool")
	public ResultVO editSchool(@RequestParam Map<String, String> params) {
		return school_impl.findSchoolById(params);
	}
	@ResponseBody
	@PostMapping("/schoolList")
	public ResultVO findAllSchool(@RequestParam Map<String, String> params ,HttpSession session) {
		return school_impl.findAllSchool(params,session);
	}
	@ResponseBody
	@PostMapping("/saveSchool")
	public ResultVO saveSchool(@RequestParam Map<String, String> params ,HttpSession session) {
		return school_impl.alterSchool(params ,session);
	}
	@ResponseBody
	@PostMapping("/deleteSchool")
	public ResultVO deleteSchool(@RequestParam Map<String, String> params,HttpSession session) {
		return school_impl.deleteSchool(params,session);
	}
	
	
	
	/**
	 * 后台新增方法
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/addUser")
	public ResultVO addUser(@RequestParam Map<String, String> params) {
		return user_impl.insertUser(params);
	}
	
	/**
	 * 账户列表方法
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/userList")
	public ResultVO userList(@RequestParam Map<String, String> params,HttpSession session) {
		return user_impl.findAll(params,session);
	}
	
	/**
	 * 账号删除方法
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/deleteUser")
	public ResultVO deleteUser(@RequestParam Map<String, String> params) {
		return user_impl.deleteUser(Integer.valueOf(params.get("id")));
	}
	
	@ResponseBody
	@PostMapping("/addUserSchool")
	public ResultVO addUserSchool(@RequestParam Map<String, String> params) {
		return user_impl.addUserSchool(params);
	}
	
	@ResponseBody
	@PostMapping("/deleteUserSchool")
	public ResultVO deleteUserSchool(@RequestParam Map<String, String> params) {
		return user_impl.deleteUserSchool(params);
	}
	
}
