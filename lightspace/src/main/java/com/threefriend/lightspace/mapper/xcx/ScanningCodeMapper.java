package com.threefriend.lightspace.mapper.xcx;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 店员扫码的记录
 * @author Administrator
 *
 */
@Entity
public class ScanningCodeMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	//订单id
	private Integer orderId;
	//商品名称
	private String productName;
	//商品id
	private Integer productId;
	//规格名称
	private String specificationsName;
	//规格id
	private Integer specificationsId;
	//店员id
	private Integer clertId;
	//店员姓名
	private String clertName;
	//学生姓名
	private String studentName;
	//体验完成时间
	private String genTime;
	//合作机构
	private Integer partnershipId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSpecificationsName() {
		return specificationsName;
	}

	public void setSpecificationsName(String specificationsName) {
		this.specificationsName = specificationsName;
	}

	public Integer getSpecificationsId() {
		return specificationsId;
	}

	public void setSpecificationsId(Integer specificationsId) {
		this.specificationsId = specificationsId;
	}

	public Integer getClertId() {
		return clertId;
	}

	public void setClertId(Integer clertId) {
		this.clertId = clertId;
	}

	public String getClertName() {
		return clertName;
	}

	public void setClertName(String clertName) {
		this.clertName = clertName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	

	public Integer getPartnershipId() {
		return partnershipId;
	}

	public void setPartnershipId(Integer partnershipId) {
		this.partnershipId = partnershipId;
	}

	public String getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(genTime);
	}
	
	
}
