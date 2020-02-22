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
	// 所属地区
	@Column(name = "region_name")
	private String regionName;
	// 所属地区id
	@Column(name = "region_id")
	private Integer regionId;
	// 所属学校
	@Column(name = "school_name")
	private String schoolName;
	// 所属学校id
	@Column(name = "school_id")
	private Integer schoolId;
	// 所属班级
	@Column(name = "classes_name")
	private String classesName;
	// 所属班级id
	@Column(name = "classes_id")
	private Integer classesId;
	//学生id
	@Column(name = "student_id")
	private Integer studentId;
	// 姓名
	@Column(name = "student_name")
	private String studentName;
	// 左眼裸眼视力
	@Column(name = "vision_left")
	private Double visionLeft;
	// 右眼裸眼视力
	@Column(name = "vision_right")
	private Double visionRight;
	// 检测距离
	private String distance;
	// 检测数量
	private String number;
	// 起止视标
	@Column(name = "begin_end")
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
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getClassesName() {
		return classesName;
	}
	public void setClassesName(String classesName) {
		this.classesName = classesName;
	}
	public Integer getClassesId() {
		return classesId;
	}
	public void setClassesId(Integer classesId) {
		this.classesId = classesId;
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
