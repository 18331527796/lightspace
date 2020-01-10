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
public class StudentMapper {

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
	// 姓名
	private String name;
	// 性别
	private String gender;
	// 年龄
	private Integer age;
	// 身高
	private Integer height;
	// 坐姿身高
	@Column(name = "sitting_height")
	private Integer sittingHeight;
	// 坐姿用椅高度
	@Column(name = "chair_height")
	private Integer chairHeight;
	// 体重
	private Integer weight;
	// 性格
	private String nature;
	// 是否矫正 (0:未校正 1:未校正)
	private Integer correct;
	// 备注
	private String description;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getSittingHeight() {
		return sittingHeight;
	}
	public void setSittingHeight(Integer sittingHeight) {
		this.sittingHeight = sittingHeight;
	}
	public Integer getChairHeight() {
		return chairHeight;
	}
	public void setChairHeight(Integer chairHeight) {
		this.chairHeight = chairHeight;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
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



}
