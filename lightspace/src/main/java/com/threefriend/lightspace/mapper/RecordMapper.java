package com.threefriend.lightspace.mapper;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 基础数据表
 * 
 * @author Administrator
 *
 */
@Entity
public class RecordMapper {

	@Id
	@GeneratedValue
	// 主键
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
	// 左眼眼轴长度
	@Column(name = "eye_axis_length_left")
	private Integer eyeAxisLengthLeft;
	// 右眼眼轴长度
	@Column(name = "eye_axis_length_right")
	private Integer eyeAxisLengthRight;
	// 左眼曲率
	@Column(name = "curvature_left")
	private Integer curvatureLeft;
	// 右眼曲率
	@Column(name = "curvature_right")
	private Integer curvatureRight;
	// 左眼屈光度
	@Column(name = "diopter_left")
	private String diopterLeft;
	// 右眼屈光度
	@Column(name = "diopter_right")
	private String diopterRight;
	//右眼矫正视力
	@Column(name = "cva_right")
	private Double cvaRight;
	//左眼矫正视力
	@Column(name = "cva_left")
	private Double cvaLeft;
	//创建时间
	@Column(name = "gen_time")
	private Date genTime;
	
	
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
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
	public String getDiopterLeft() {
		return diopterLeft;
	}
	public void setDiopterLeft(String diopterLeft) {
		this.diopterLeft = diopterLeft;
	}
	public String getDiopterRight() {
		return diopterRight;
	}
	public void setDiopterRight(String diopterRight) {
		this.diopterRight = diopterRight;
	}
	public Double getCvaRight() {
		return cvaRight;
	}
	public void setCvaRight(Double cvaRight) {
		this.cvaRight = cvaRight;
	}
	public Double getCvaLeft() {
		return cvaLeft;
	}
	public void setCvaLeft(Double cvaLeft) {
		this.cvaLeft = cvaLeft;
	}
	public Integer getEyeAxisLengthLeft() {
		return eyeAxisLengthLeft;
	}
	public void setEyeAxisLengthLeft(Integer eyeAxisLengthLeft) {
		this.eyeAxisLengthLeft = eyeAxisLengthLeft;
	}
	public Integer getEyeAxisLengthRight() {
		return eyeAxisLengthRight;
	}
	public void setEyeAxisLengthRight(Integer eyeAxisLengthRight) {
		this.eyeAxisLengthRight = eyeAxisLengthRight;
	}
	public Integer getCurvatureLeft() {
		return curvatureLeft;
	}
	public void setCurvatureLeft(Integer curvatureLeft) {
		this.curvatureLeft = curvatureLeft;
	}
	public Integer getCurvatureRight() {
		return curvatureRight;
	}
	public void setCurvatureRight(Integer curvatureRight) {
		this.curvatureRight = curvatureRight;
	}
	
	

}
