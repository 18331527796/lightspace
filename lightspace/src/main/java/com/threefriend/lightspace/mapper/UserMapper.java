package com.threefriend.lightspace.mapper;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *	后台管理用户表
 */
@Entity
public class UserMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 用户姓名
	private String name;
	// 用户账号
	@Column(name="login_name")
	private String loginName;
	// 用户密码
	private String password;
	// 创建时间
	@Column(name="gen_time")
	private Date genTime;
	//对应的学校
	@Column(name="school_id")
	private Integer schoolId;
	//对应的班级
	@Column(name="classes_id")
	private Integer classesId;
	//角色名称
	@Column(name="role_name")
	private String roleName;
	
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

}
