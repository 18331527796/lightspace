package com.threefriend.lightspace.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 排座顺序表
 * @author Administrator
 *
 */
@Entity
public class SortMapper {

	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//名称
	private String name;
	//班级id
	private Integer classId;
	//type
	private Integer type;
	//座位表
	private String sort;
	//前台用的添加间隔用
	private String mr;
	//创建时间
	private Date  genTime;
	//保存时间
	private Long  endTime;
	//给前台
	private String date;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
		this.date = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(genTime);
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public String getMr() {
		return mr;
	}
	public void setMr(String mr) {
		this.mr = mr;
	}
	@Override
	public String toString() {
		return "SortMapper [id=" + id + ", name=" + name + ", classId=" + classId + ", type=" + type + ", sort=" + sort
				+ ", mr=" + mr + ", genTime=" + genTime + ", endTime=" + endTime + ", date=" + date + "]";
	}
	
}
