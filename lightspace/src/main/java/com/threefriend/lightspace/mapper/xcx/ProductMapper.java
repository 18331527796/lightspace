package com.threefriend.lightspace.mapper.xcx;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *  商品表
 */
@Entity
public class ProductMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	
	private String name; 

	private String picture;
	
	private String details;
	
	private Integer PartnershipId;
	
	private String PartnershipName;
	
	private Integer disPlayBuyer = 1;
	
	private String genTime;

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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getPartnershipId() {
		return PartnershipId;
	}

	public void setPartnershipId(Integer partnershipId) {
		PartnershipId = partnershipId;
	}

	public String getPartnershipName() {
		return PartnershipName;
	}

	public void setPartnershipName(String partnershipName) {
		PartnershipName = partnershipName;
	}

	public Integer getDisPlayBuyer() {
		return disPlayBuyer;
	}

	public void setDisPlayBuyer(Integer disPlayBuyer) {
		this.disPlayBuyer = disPlayBuyer;
	}

	public String getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(genTime);
	}
	
	
}
