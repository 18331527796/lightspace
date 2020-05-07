package com.threefriend.lightspace.mapper.xcx;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *  积分表
 */
@Entity
public class IntegralMapper {

	
	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	//用户Id、
	private Integer parentId;
	//收支标识(0:支出，1:收入)
	private Integer state;
	//本条的积分数
	private Long integral;
	//本条明细
	private String detailed;
	//本条的创建时间
	private Date genTime;
	//前台时间
	private String date;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDetailed() {
		return detailed;
	}
	public void setDetailed(String detailed) {
		this.detailed = detailed;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.genTime = genTime;
		this.date = simpleDateFormat.format(genTime);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	
}
