package com.threefriend.lightspace.vo;

import java.util.Date;
import java.util.List;

import com.threefriend.lightspace.mapper.SchoolMapper;

public class UserVO {

	// 主键
	private Integer id;
	// 用户姓名
	private String name;
	// 用户账号
	private String loginName;
	// 用户密码
	private String password;
	// 创建时间
	private Date genTime;
	//对应的学校
	private Integer schoolId;
	//对应的班级
	private Integer classesId;
	//角色名称
	private String roleName;
	//地区id
	private Integer regionId;
	//地区名称
	private String regionName;
	
	private List<SchoolMapper> children = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getClassesId() {
		return classesId;
	}

	public void setClassesId(Integer classesId) {
		this.classesId = classesId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public List<SchoolMapper> getChildren() {
		return children;
	}

	public void setChildren(List<SchoolMapper> children) {
		this.children = children;
	}
	
	
}
