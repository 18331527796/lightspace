package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.xcx.ProductMapper;
import com.threefriend.lightspace.mapper.xcx.SpecificationsMapper;

public class ProductVO {
	
	private Integer id;
	
	private String name; 
	
	private Long Integral;
	
	private List<SpecificationsMapper> specificationsList;

	private List<String> pictures = new ArrayList<>();
	
	private List<ProductVO> productList = new ArrayList<>();
	
	private String details;
	
	private String genTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<ProductVO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductVO> productList) {
		this.productList = productList;
	}

	public List<SpecificationsMapper> getSpecificationsList() {
		return specificationsList;
	}

	public void setSpecificationsList(List<SpecificationsMapper> specificationsList) {
		this.specificationsList = specificationsList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = UrlEnums.IMG_URL.getUrl()+details;
	}

	public String getGenTime() {
		return genTime;
	}

	public void setGenTime(String genTime) {
		this.genTime = genTime;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> picture) {
		this.pictures = picture;
	}

	public Long getIntegral() {
		return Integral;
	}

	public void setIntegral(Long integral) {
		Integral = integral;
	}
	

	
}
