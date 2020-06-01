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

	public IntegralMapper(Integer studentId,Integer state,Long integral,String detailed,Date genTime) {
		this.studentId=studentId;
		this.state=state;
		this.integral=integral;
		this.detailed=detailed;
		this.genTime=genTime;
		this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(genTime);
	}
	
	public IntegralMapper() {
	}
	
	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	//学校Id、
	private Integer studentId;
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
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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
