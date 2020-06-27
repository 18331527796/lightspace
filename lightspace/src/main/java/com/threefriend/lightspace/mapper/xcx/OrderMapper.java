package com.threefriend.lightspace.mapper.xcx;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.threefriend.lightspace.enums.OrderStatusEnum;

/**
 * 订单
 * @author Administrator
 *
 */
@Entity
public class OrderMapper {
	
	@Id
	@GeneratedValue
	private Integer id;
	private Integer studentId;//学生id
	private Integer schoolId;//学校id
	private String schoolName;//学校名称
	private String phone;//联系电话
	private String contacts;//联系人
	private String address;//地址
	private Integer productId;//商品id
	private String productName;//商品名称
	private Integer specificationId;//商品规格
	private Integer number = 0;//数量
	private String status ;//订单状态
	private Long paymoney = 0l;//支付金额
	private Integer freight = 0;//运费
	private String delivrytype;//配送方式 
	private String successtime;//订单完成时间
	private String deliverynumber;//运单号
	private String partnership;//合作机构
	private String partnershipAddress;//合作机构地址
	private String remark;//备注
	private String gentime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//创建时间
	private String pic;
	private Integer display; //1 : 显示  2 : 不显示
	private String specificationName;
	private Date genTimeDate = new Date();
	
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
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getGentime() {
		return gentime;
	}
	public void setGentime(String gentime) {
		this.gentime = gentime;
	}
	public void setSuccesstime(String successtime) {
		this.successtime = successtime;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
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
	public Integer getFreight() {
		return freight;
	}
	public void setFreight(Integer freight) {
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
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	public String getSpecificationName() {
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
	public Date getGenTimeDate() {
		return genTimeDate;
	}
	public void setGenTimeDate(Date genTimeDate) {
		this.genTimeDate = genTimeDate;
	}
	public String getPartnership() {
		return partnership;
	}
	public void setPartnership(String partnership) {
		this.partnership = partnership;
	}
	public String getPartnershipAddress() {
		return partnershipAddress;
	}
	public void setPartnershipAddress(String partnershipAddress) {
		this.partnershipAddress = partnershipAddress;
	}
	
	
}
