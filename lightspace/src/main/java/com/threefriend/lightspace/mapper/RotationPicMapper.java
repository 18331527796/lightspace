package com.threefriend.lightspace.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 商城首页轮播图
 * @author Administrator
 *
 */
@Entity
public class RotationPicMapper {

	@Id
	@GeneratedValue
	//主键
	private Integer id;
	
	private Integer productId;
	
	private String productName;
	
	private String path;
	
	private String genTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(genTime);
	}
	
	
}
