package com.threefriend.lightspace.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {
	
	@PostMapping("/hello")
	public String hello() {
		
		return "ping通了没";
	}

}
