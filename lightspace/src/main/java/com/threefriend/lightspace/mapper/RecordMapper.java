package com.threefriend.lightspace.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 学生表
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
	private String visionLeft;
	// 右眼裸眼视力
	@Column(name = "vision_right")
	private String visionRight;
	// 左眼眼轴长度
	@Column(name = "eye_axis_length_left")
	private String eyeAxisLengthLeft;
	// 右眼眼轴长度
	@Column(name = "eye_axis_length_right")
	private String eyeAxisLengthRight;
	// 左眼曲率
	@Column(name = "curvature_left")
	private String curvatureLeft;
	// 右眼曲率
	@Column(name = "curvature_right")
	private String curvatureRight;
	// 左眼屈光度
	@Column(name = "diopter_left")
	private String diopterLeft;
	// 右眼屈光度
	@Column(name = "diopter_right")
	private String diopterRight;
	//右眼矫正视力
	@Column(name = "cva_right")
	private String cvaRight;
	//左眼矫正视力
	@Column(name = "cva_left")
	private String cvaLeft;
	
	
	
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
	public String getVisionLeft() {
		return visionLeft;
	}
	public void setVisionLeft(String visionLeft) {
		this.visionLeft = visionLeft;
	}
	public String getVisionRight() {
		return visionRight;
	}
	public void setVisionRight(String visionRight) {
		this.visionRight = visionRight;
	}
	public String getEyeAxisLengthLeft() {
		return eyeAxisLengthLeft;
	}
	public void setEyeAxisLengthLeft(String eyeAxisLengthLeft) {
		this.eyeAxisLengthLeft = eyeAxisLengthLeft;
	}
	public String getEyeAxisLengthRight() {
		return eyeAxisLengthRight;
	}
	public void setEyeAxisLengthRight(String eyeAxisLengthRight) {
		this.eyeAxisLengthRight = eyeAxisLengthRight;
	}
	public String getCurvatureLeft() {
		return curvatureLeft;
	}
	public void setCurvatureLeft(String curvatureLeft) {
		this.curvatureLeft = curvatureLeft;
	}
	public String getCurvatureRight() {
		return curvatureRight;
	}
	public void setCurvatureRight(String curvatureRight) {
		this.curvatureRight = curvatureRight;
	}
	public String getDiopterLeft() {
		return diopterLeft;
	}
	public void setDiopterLeft(String diopterLeft) {
		this.diopterLeft = diopterLeft;
	}
	public String getCvaRight() {
		return cvaRight;
	}
	public void setCvaRight(String cvaRight) {
		this.cvaRight = cvaRight;
	}
	public String getCvaLeft() {
		return cvaLeft;
	}
	public void setCvaLeft(String cvaLeft) {
		this.cvaLeft = cvaLeft;
	}
	public String getDiopterRight() {
		return diopterRight;
	}
	public void setDiopterRight(String diopterRight) {
		this.diopterRight = diopterRight;
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
	
	

}
