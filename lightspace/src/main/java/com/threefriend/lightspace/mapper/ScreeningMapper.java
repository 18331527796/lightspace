package com.threefriend.lightspace.mapper;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.threefriend.lightspace.util.OptotypeUtils;

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
	//生日
	private String birthday;
	// 左眼裸眼视力
	private String visionLeft;
	private String vision5Left;
	private Double visionLeftStr;
	// 右眼裸眼视力
	private String visionRight;
	private String vision5Right;
	private Double visionRightStr;
	// 检测距离
	private String distance;
	// 检测数量
	private String number;
	// 起止视标
	private String beginEnd;
	//创建时间
	private Date genTime;
	private String date;
	
	
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getVision5Left() {
		return vision5Left;
	}
	public void setVision5Left(String vision5Left) {
		this.vision5Left = vision5Left;
	}
	public String getVision5Right() {
		return vision5Right;
	}
	public void setVision5Right(String vision5Right) {
		this.vision5Right = vision5Right;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVisionLeft() {
		return visionLeft;
	}
	public void setVisionLeft(String visionLeft) {
		this.visionLeft = visionLeft;
	}
	public Double getVisionLeftStr() {
		return visionLeftStr;
	}
	public void setVisionLeftStr(Double visionLeftStr) {
		this.visionLeftStr = visionLeftStr;
		this.visionLeft = OptotypeUtils.vision2vision5(visionLeftStr);
		this.vision5Left = OptotypeUtils.vision2onlyvision5(visionLeftStr);
	}
	public String getVisionRight() {
		return visionRight;
	}
	public void setVisionRight(String visionRight) {
		this.visionRight = visionRight;
	}
	public Double getVisionRightStr() {
		return visionRightStr;
	}
	public void setVisionRightStr(Double visionRightStr) {
		this.visionRightStr = visionRightStr;
		this.visionRight = OptotypeUtils.vision2vision5(visionRightStr);
		this.vision5Right = OptotypeUtils.vision2onlyvision5(visionRightStr);
	}
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
