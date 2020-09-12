package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.TaskExamineStatesEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.TaskExamineMapper;

public class TaskExamineVO {
	
	private Integer id ;
	
	private String avatarUrl;
	
	private String nickName;
	
	private String examineStatus;
	
	private Integer classId;
	
	private String className;
	
	private Integer schoolId;
	
	private String schoolName;
	
	private Integer studentId;
	
	private String studentName;
	//点赞
	private Integer fabulous;
	//是否点赞 1 : 点了 2：没点
	private Integer isFabulous;
	//送花
	private Integer flower;
	
	private String contents;

	private Date genTime;
	
	private String date;
	
	private List<String> fabulousList = new ArrayList<>();
	
	private List<String> path = new ArrayList<>();
	
	public TaskExamineVO (TaskExamineMapper po){
		this.id = po.getId();
		this.contents = po.getContents();
		this.fabulous = po.getFabulous();
		this.flower = po.getFlower();
		this.avatarUrl = po.getAvatarUrl();
		this.nickName = po.getNickName();
		this.classId = po.getClassId();
		this.className = po.getClassName();
		if(po.getExamineStatus()==TaskExamineStatesEnum.EXAMINE.getCode()) {
			this.examineStatus =TaskExamineStatesEnum.EXAMINE.getMessage();
		}else {
			this.examineStatus = TaskExamineStatesEnum.UNEXAMINE.getMessage();
		}
		this.schoolId = po.getSchoolId();
		this.schoolName = po.getSchoolName();
		this.studentId = po.getStudentId();
		this.studentName = po.getStudentName();
		this.date = po.getDate();
		this.genTime = po.getGenTime();
		if(!StringUtils.isEmpty(po.getPath())) {
			String[] split = po.getPath().split(",");
			for (String string : split) {
				this.path.add(UrlEnums.IMG_URL.getUrl()+string);
			}
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
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

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getFabulous() {
		return fabulous;
	}

	public void setFabulous(Integer fabulous) {
		this.fabulous = fabulous;
	}

	public Integer getFlower() {
		return flower;
	}

	public void setFlower(Integer flower) {
		this.flower = flower;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Integer getIsFabulous() {
		return isFabulous;
	}

	public void setIsFabulous(Integer isFabulous) {
		this.isFabulous = isFabulous;
	}

	public List<String> getFabulousList() {
		return fabulousList;
	}

	public void setFabulousList(List<String> fabulousList) {
		this.fabulousList = fabulousList;
	}
	
	
}
