package com.threefriend.lightspace.mapper;

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
	@Column(name="class_id")
	private Integer classId;
	//type
	@Column(name="type")
	private Integer type;
	//座位表
	@Column(name="sort")
	private String sort;
	//创建时间
	@Column(name="gen_time")
	private Date  genTime;
	//保存时间
	@Column(name="end_time")
	private Date  endTime;
	
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
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
