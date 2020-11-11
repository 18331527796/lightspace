package com.threefriend.lightspace.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DiopterMapper {
	
	public DiopterMapper(StudentMapper po ) {
		this.diopterLeft="暂无数据";
		this.diopterRight="暂无数据";
		this.studentId=po.getId();
		this.classId=po.getClassesId();
		this.className=po.getClassesName();
		this.schoolId=po.getSchoolId();
		this.schoolName=po.getSchoolName();
		this.myIntegral=po.getMyIntegral();
		this.birthday =po.getBirthday();
		this.pd="暂无数据";
		this.ds1L="暂无数据";
		this.dc1L="暂无数据";
		this.axis1L="暂无数据";
		this.ds1R="暂无数据";
		this.dc1R="暂无数据";
		this.axis1R="暂无数据";
		this.ghL="暂无数据";
		this.gvL="暂无数据";
		this.ghR="暂无数据";
		this.gvR="暂无数据";
		this.gender=po.getGender();
		this.studentName=po.getName();
		this.genTime=null;
	}
	
	public DiopterMapper() {
	}
	
	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 左眼屈光度
	private String diopterLeft;
	// 右眼屈光度
	private String diopterRight;
	//学生id
	private Integer studentId;
	private Integer classId;
	private String className;
	private Integer schoolId;
	private String schoolName;
	//瞳距
	private String pd;
	//球
	private String ds1L;
	//柱
	private String dc1L;
	//度
	private String axis1L;
	private String ds1R;
	private String dc1R;
	private String axis1R;
	private String ghL;
	private String gvL;
	private String ghR;
	private String gvR;
	//性别
	private Integer gender;
	// 姓名
	private String studentName;
	//积分
	private Long myIntegral;
	//生日
	private String birthday;
	
	//
	private String genTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Long getMyIntegral() {
		return myIntegral;
	}

	public void setMyIntegral(Long myIntegral) {
		this.myIntegral = myIntegral;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
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
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getGenTime() {
		return genTime;
	}
	public void setGenTime(String genTime) {
		this.genTime = genTime;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getDs1L() {
		return ds1L;
	}

	public void setDs1L(String ds1l) {
		ds1L = ds1l;
	}

	public String getDc1L() {
		return dc1L;
	}

	public void setDc1L(String dc1l) {
		dc1L = dc1l;
	}

	public String getAxis1L() {
		return axis1L;
	}

	public void setAxis1L(String axis1l) {
		axis1L = axis1l;
	}

	public String getDs1R() {
		return ds1R;
	}

	public void setDs1R(String ds1r) {
		ds1R = ds1r;
	}

	public String getDc1R() {
		return dc1R;
	}

	public void setDc1R(String dc1r) {
		dc1R = dc1r;
	}

	public String getAxis1R() {
		return axis1R;
	}

	public void setAxis1R(String axis1r) {
		axis1R = axis1r;
	}
	
	public String getGhL() {
		return ghL;
	}
	public void setGhL(String ghL) {
		this.ghL = ghL;
	}
	public String getGvL() {
		return gvL;
	}
	public void setGvL(String gvL) {
		this.gvL = gvL;
	}
	public String getGhR() {
		return ghR;
	}
	public void setGhR(String ghR) {
		this.ghR = ghR;
	}
	public String getGvR() {
		return gvR;
	}
	public void setGvR(String gvR) {
		this.gvR = gvR;
	}
	public void setStudentMapper(StudentMapper po) {
		this.gender = po.getGender();
		this.studentName = po.getName();
		this.studentId = po.getId();
		this.classId = po.getClassesId();
		this.className = po.getClassesName();
		this.schoolId = po.getSchoolId();
		this.schoolName = po.getSchoolName();
		this.birthday = po.getBirthday();
		this.myIntegral = po.getMyIntegral();
	}
	
}
