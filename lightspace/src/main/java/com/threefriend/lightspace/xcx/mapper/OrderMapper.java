package com.threefriend.lightspace.xcx.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderMapper {
	
	@Id
	@GeneratedValue
	private Integer id;
	private Integer schoolId;//学校id
	private Integer parentId;//家长
	private String phone;//联系电话
	private String contacts;//联系人
	private String address;//地址
	private Integer productId;//商品id
	private Integer specificationId;//商品规格
	private Integer number;//数量
	private String status;//订单状态
	private Long paymoney;//支付金额
	private Long freight;//运费
	private String delivrytype;//配送方式 
	private String successtime;//订单完成时间
	private String deliverynumber;//运单号
	private String remark;//备注
	private String gentime;//创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(Integer specificationId) {
		this.specificationId = specificationId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(Long paymoney) {
		this.paymoney = paymoney;
	}
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public String getDelivrytype() {
		return delivrytype;
	}
	public void setDelivrytype(String delivrytype) {
		this.delivrytype = delivrytype;
	}
	public String getSuccesstime() {
		return successtime;
	}
	public void setSuccesstime(Date successtime) {
		this.successtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(successtime);
	}
	public String getDeliverynumber() {
		return deliverynumber;
	}
	public void setDeliverynumber(String deliverynumber) {
		this.deliverynumber = deliverynumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGentime() {
		return gentime;
	}
	public void setGentime(Date gentime) {
		this.gentime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gentime);
	}
	
}
