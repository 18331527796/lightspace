package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.service.Impl.RotationPicServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 轮播图
 * @author Administrator
 *
 */
@RestController
public class RotationPicController {

	@Autowired
	private RotationPicServiceImpl rotationPic_impl;
	
	@PostMapping("/rotationList")
	public ResultVO rotationList(@RequestParam Map<String, String> params) {
		return rotationPic_impl.RotationPicList(params);
	}
	
	@PostMapping("/addRotationPic")
	public ResultVO addRotationPic(@RequestParam Map<String, String> params,@RequestParam("rotationPic") MultipartFile pic) {
		return rotationPic_impl.addRotationPic(params, pic);
	}
	
	@PostMapping("/editRotationPic")
	public ResultVO editRotationPic(@RequestParam Map<String, String> params) {
		return rotationPic_impl.editRotationPic(params);
	}
	
	@PostMapping("/saveRotationPic")
	public ResultVO saveRotationPic(@RequestParam Map<String, String> params,@RequestParam("rotationPic") MultipartFile pic) {
		return rotationPic_impl.saveRotationPic(params, pic);
	}
	
	@PostMapping("/deleteRotationPic")
	public ResultVO deleteRotationPic(@RequestParam Map<String, String> params) {
		return rotationPic_impl.deleteRotationPic(params);
	}
}
