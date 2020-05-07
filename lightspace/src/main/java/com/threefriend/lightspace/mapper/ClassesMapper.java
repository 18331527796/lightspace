package com.threefriend.lightspace.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 班级表
 * 
 * @author Administrator
 *
 */
@Entity
public class ClassesMapper {
	

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 班级名称
	@Column(name="name")
	private String className;
	// 班级人数
	private Integer volume;
	// 教室长度
	@Column(name="room_length")
	private String roomLength;
	// 教室宽度
	@Column(name="room_width")
	private String roomWidth;
	// 黑板长度
	@Column(name="bb_length")
	private String bbLength;
	// 是否实验班（0：不是 1：是）
	private Integer experiment;
	// 所属学校id
	@Column(name="school_id")
	private Integer schoolId;
	// 所属学校名称
	@Column(name="school_name")
	private String schoolName;
	// 所属地区
	@Column(name="region_name")
	private String regionName;
	// 所属地区id
	@Column(name="region_id")
	private Integer regionId;
	//是否毕业
	private Integer finish;
	//几年级
	private Integer grade;
	// 备注			
	private String description = "";
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getFinish() {
		return finish;
	}
	public void setFinish(Integer finish) {
		this.finish = finish;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
		String substring = className.substring(0, 1);
		switch (substring) {
		case "一":this.grade=1;
			break;
		case "二":this.grade=2;
			break;
		case "三":this.grade=3;
			break;
		case "四":this.grade=4;
			break;
		case "五":this.grade=5;
			break;
		case "六":this.grade=6;
			break;
		default:
			this.grade=0;
			break;
		}
	}
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public String getRoomLength() {
		return roomLength;
	}
	public void setRoomLength(String roomLength) {
		this.roomLength = roomLength;
	}
	public String getRoomWidth() {
		return roomWidth;
	}
	public void setRoomWidth(String roomWidth) {
		this.roomWidth = roomWidth;
	}
	public String getBbLength() {
		return bbLength;
	}
	public void setBbLength(String bbLength) {
		this.bbLength = bbLength;
	}
	public Integer getExperiment() {
		return experiment;
	}
	public void setExperiment(Integer experiment) {
		this.experiment = experiment;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
