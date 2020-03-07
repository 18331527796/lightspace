package com.threefriend.lightspace.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 任务列表
 */
public class TaskVO {

	private Integer id;
	//标题
	private String title;
	//内容
	private String content;
	//积分
	private Long integral;
	//完成标识 (0:未完成 1:已完成)
	private Integer success;
	//创建时间
	private Date genTime;
	
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	
	
	
}
