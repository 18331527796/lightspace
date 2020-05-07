package com.threefriend.lightspace.mapper.xcx;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.threefriend.lightspace.util.OptotypeUtils;

/**
 * 筛查表（戴镜）
 * @author Administrator
 *
 */
@Entity
public class ScreeningWearMapper {

	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//学生id
	private Integer studentId;
	private Integer classId;
	private String className;
	private Integer schoolId;
	private String schoolName;
	//性别
	private Integer gender;
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
	// 左眼过程
	private String processLeft;
	// 右眼过程
	private String processRight;
	//创建时间
	private Date genTime;
	private String date;
	private String time;
	
	
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
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
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat Format = new SimpleDateFormat("HH:mm:ss");
		this.genTime = genTime;
		this.date = simpleDateFormat.format(genTime);
		this.time = Format.format(genTime);
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getProcessLeft() {
		return processLeft;
	}
	public void setProcessLeft(String processLeft) {
		this.processLeft = processLeft;
	}
	public String getProcessRight() {
		return processRight;
	}
	public void setProcessRight(String processRight) {
		this.processRight = processRight;
	}
	public String getTime() {
		return time;
	}
	
	
	
}
