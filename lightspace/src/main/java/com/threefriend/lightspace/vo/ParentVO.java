package com.threefriend.lightspace.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.threefriend.lightspace.mapper.StudentMapper;

public class ParentVO {

	// 主键
	private Integer id;
	// 家长手机号
	private Long phone;
	// 注册时间
	private String genTime;
	//openid
	private String openId;
	
	private List<StudentMapper> student= new ArrayList<StudentMapper>();
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.genTime = format.format(genTime);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public List<StudentMapper> getStudent() {
		return student;
	}

	public void setStudent(List<StudentMapper> student) {
		this.student = student;
	}}
