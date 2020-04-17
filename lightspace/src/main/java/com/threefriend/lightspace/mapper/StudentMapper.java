package com.threefriend.lightspace.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class StudentMapper {

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
	// 姓名
	private String name;
	//生日
	private String birthday;
	// 性别
	private Integer gender;
	// 年龄
	private Integer age;
	// 身高
	private String height;
	// 坐姿身高
	@Column(name = "sitting_height")
	private String sittingHeight;
	// 坐姿用椅高度
	@Column(name = "chair_height")
	private String chairHeight;
	// 体重
	private String weight;
	// 家长联系方式
	@Column(name = "patent_phone")
	private String parentPhone;
	// 性格
	private String nature ="";
	// 是否矫正 (0:未校正 1:未校正)
	private Integer correct;
	//上次检测时间
	private String lastTime;
	//上次推送时间
	private Date sendTime;
	// 备注
	private String description = "";
	
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getParentPhone() {
		return parentPhone;
	}
	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getSittingHeight() {
		return sittingHeight;
	}
	public void setSittingHeight(String sittingHeight) {
		this.sittingHeight = sittingHeight;
	}
	public String getChairHeight() {
		return chairHeight;
	}
	public void setChairHeight(String chairHeight) {
		this.chairHeight = chairHeight;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public Integer getCorrect() {
		return correct;
	}
	public void setCorrect(Integer correct) {
		this.correct = correct;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date date) {
		DateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.lastTime = Format.format(date);
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}



}