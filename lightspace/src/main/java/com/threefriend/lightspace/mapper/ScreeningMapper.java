package com.threefriend.lightspace.mapper;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 筛查表
 * @author Administrator
 *
 */
@Entity
public class ScreeningMapper {

	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//学生id
	private Integer studentId;
	// 姓名
	private String studentName;
	// 左眼裸眼视力
	private Double visionLeft;
	// 右眼裸眼视力
	private Double visionRight;
	// 检测距离
	private String distance;
	// 检测数量
	private String number;
	// 起止视标
	private String beginEnd;
	//创建时间
	@Column(name = "gen_time")
	private Date genTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Double getVisionLeft() {
		return visionLeft;
	}
	public void setVisionLeft(Double visionLeft) {
		this.visionLeft = visionLeft;
	}
	public Double getVisionRight() {
		return visionRight;
	}
	public void setVisionRight(Double visionRight) {
		this.visionRight = visionRight;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getBeginEnd() {
		return beginEnd;
	}
	public void setBeginEnd(String beginEnd) {
		this.beginEnd = beginEnd;
	}
	
	
	
}
