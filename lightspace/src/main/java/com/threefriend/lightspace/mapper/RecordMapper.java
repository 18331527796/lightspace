package com.threefriend.lightspace.mapper;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.threefriend.lightspace.util.OptotypeUtils;

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
	private String regionName;
	// 所属地区id
	private Integer regionId;
	// 所属学校
	private String schoolName;
	// 所属学校id
	private Integer schoolId;
	// 所属班级
	private String classesName;
	// 所属班级id
	private Integer classesId;
	//学生id
	private Integer studentId;
	// 姓名
	private String studentName;
	// 左眼裸眼视力
	private String visionLeft;
	private Double visionLeftStr;
	// 右眼裸眼视力
	private String visionRight;
	private Double visionRightStr;
	// 左眼眼轴长度
	private Double eyeAxisLengthLeft;
	// 右眼眼轴长度
	private Double eyeAxisLengthRight;
	// 左眼曲率
	private Double curvatureLeft;
	// 右眼曲率
	private Double curvatureRight;
	// 左眼屈光度
	private String diopterLeft = "";
	// 右眼屈光度
	private String diopterRight = "";
	//右眼矫正视力
	private Double cvaRight = 0.0;
	//左眼矫正视力
	private Double cvaLeft = 0.0;
	//创建时间
	private Date genTime;
	
	
	public Double getVisionLeftStr() {
		return visionLeftStr;
	}
	public void setVisionLeftStr(Double visionLeftStr) {
		this.visionLeftStr = visionLeftStr;
		this.visionLeft=OptotypeUtils.vision2vision5(visionLeftStr);
	}
	public Double getVisionRightStr() {
		return visionRightStr;
	}
	public void setVisionRightStr(Double visionRightStr) {
		this.visionRightStr = visionRightStr;
		this.visionRight=OptotypeUtils.vision2vision5(visionRightStr);
	}
	public String getVisionLeft() {
		return visionLeft;
	}
	public String getVisionRight() {
		return visionRight;
	}
	public void setVisionLeft(String visionLeft) {
		this.visionLeft = visionLeft;
	}
	public void setVisionRight(String visionRight) {
		this.visionRight = visionRight;
	}
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
	public Double getEyeAxisLengthLeft() {
		return eyeAxisLengthLeft;
	}
	public void setEyeAxisLengthLeft(Double eyeAxisLengthLeft) {
		this.eyeAxisLengthLeft = eyeAxisLengthLeft;
	}
	public Double getEyeAxisLengthRight() {
		return eyeAxisLengthRight;
	}
	public void setEyeAxisLengthRight(Double eyeAxisLengthRight) {
		this.eyeAxisLengthRight = eyeAxisLengthRight;
	}
	public Double getCurvatureLeft() {
		return curvatureLeft;
	}
	public void setCurvatureLeft(Double curvatureLeft) {
		this.curvatureLeft = curvatureLeft;
	}
	public Double getCurvatureRight() {
		return curvatureRight;
	}
	public void setCurvatureRight(Double curvatureRight) {
		this.curvatureRight = curvatureRight;
	}
	
	

}
