package com.threefriend.lightspace.mapper.train;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class TrainChildrenMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	//姓名
	private String name;
	//性别
	private Integer gender;
	//生日
	private String birthday;
	//绑定的家长
	private String openId;
	
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
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@Override
	public String toString() {
		return "TrainChildrenMapper [id=" + id + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday
				+ ", openId=" + openId + "]";
	}
	
	
	
}
