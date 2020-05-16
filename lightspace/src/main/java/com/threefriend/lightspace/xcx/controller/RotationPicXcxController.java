package com.threefriend.lightspace.xcx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.Impl.RotationPicXcxServiceImpl;

@RestController
@RequestMapping("/xcx")
public class RotationPicXcxController {

	@Autowired
	private RotationPicXcxServiceImpl rotationpic_impl;
	
	@PostMapping("rotationPicList")
	public ResultVO rotationPic() {
		return rotationpic_impl.rotationPic();
	}
}
