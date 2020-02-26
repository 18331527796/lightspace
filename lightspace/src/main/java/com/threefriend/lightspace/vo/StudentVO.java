package com.threefriend.lightspace.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer Id;
	
	private String Name;
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
	// 性别
	private Integer gender;
	// 年龄
	private Integer age;
	// 身高
	private String height;
	// 坐姿身高
	private String sittingHeight;
	// 坐姿用椅高度
	private String chairHeight;
	// 体重
	private String weight;
	//家长联系方式
	private String parentPhone;
	// 性格
	private String nature ="";
	// 是否矫正 (0:未校正 1:未校正)
	private Integer correct;
	// 备注
	private String description;
	// 前台需要
	private List<Integer> stu_cat = new ArrayList<>();
	
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

	public List<Integer> getStu_cat() {
		return stu_cat;
	}

	public void setStu_cat(List<Integer> stu_cat) {
		this.stu_cat = stu_cat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	
	public StudentVO (Integer id,String name) {
		this.Id=id;
		this.Name=name;
	}
	
	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public StudentVO () {
	}
}
