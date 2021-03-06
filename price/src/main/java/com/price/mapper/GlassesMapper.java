package com.price.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GlassesMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	//产品名称
	private String name;
	//折射率
	private String refractive;
	//阿贝数
	private String abbe;
	//膜层
	private String film;
	//隐形标记
	private String covert;
	//图片
	private String picture;
	//图片
	private String customFile;
	/*//光度范围1
	private String photometric1 = "";
	//光度范围2
	private String photometric2 = "";
	//光度范围3
	private String photometric3 = "";
	//光度范围4
	private String photometric4 = "";
	//光度范围5
	private String photometric5 = "";
	//形状1(矩形：1  三角形：2  上梯形：3)
	private Integer shape1;
	//形状2
	private Integer shape2;
	//形状3
	private Integer shape3;
	//形状4
	private Integer shape4;
	//形状5
	private Integer shape5;*/
	//下加光
	private String addLightBelow;
	//现片价
	private String presentPrice;
	//定制价
	private String customPrice;
	//系列关联id
	private Integer seriesId;
	//系列名
	private String seriesName;
	//标签id
	private Integer labelId;
	//标签名
	private String labelName;
	//基准
	private String benchmark;
	//球镜
	private String sphericalMirror;
	//柱镜
	private String colonoscope;
	//现片
	private String onTheSpot;
	//偏光价
	private String polarizing = "";
	//通道
	private String passageway = "";
	//偏光定制价
	private String pricepol = "";
	//蓝光片价
	private String bluray = "";
	
	private boolean checked = false;
	 
	public String getPolarizing() {
		return polarizing;
	}
	public void setPolarizing(String polarizing) {
		this.polarizing = polarizing;
	}
	public String getPassageway() {
		return passageway;
	}
	public void setPassageway(String passageway) {
		this.passageway = passageway;
	}
	public String getPricepol() {
		return pricepol;
	}
	public void setPricepol(String pricepol) {
		this.pricepol = pricepol;
	}
	public String getBluray() {
		return bluray;
	}
	public void setBluray(String bluray) {
		this.bluray = bluray;
	}
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
	public String getRefractive() {
		return refractive;
	}
	public void setRefractive(String refractive) {
		this.refractive = refractive;
	}
	public String getAbbe() {
		return abbe;
	}
	public void setAbbe(String abbe) {
		this.abbe = abbe;
	}
	public String getFilm() {
		return film;
	}
	public void setFilm(String film) {
		this.film = film;
	}
	public String getCovert() {
		return covert;
	}
	public void setCovert(String covert) {
		this.covert = covert;
	}
	public String getAddLightBelow() {
		return addLightBelow;
	}
	public void setAddLightBelow(String addLightBelow) {
		this.addLightBelow = addLightBelow;
	}
	public String getPresentPrice() {
		return presentPrice;
	}
	public void setPresentPrice(String presentPrice) {
		this.presentPrice = presentPrice;
	}
	public String getCustomPrice() {
		return customPrice;
	}
	public void setCustomPrice(String customPrice) {
		this.customPrice = customPrice;
	}
	public Integer getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public Integer getLabelId() {
		return labelId;
	}
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String getBenchmark() {
		return benchmark;
	}
	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}
	public String getSphericalMirror() {
		return sphericalMirror;
	}
	public void setSphericalMirror(String sphericalMirror) {
		this.sphericalMirror = sphericalMirror;
	}
	public String getColonoscope() {
		return colonoscope;
	}
	public void setColonoscope(String colonoscope) {
		this.colonoscope = colonoscope;
	}
	public String getOnTheSpot() {
		return onTheSpot;
	}
	public void setOnTheSpot(String onTheSpot) {
		this.onTheSpot = onTheSpot;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getCustomFile() {
		return customFile;
	}
	public void setCustomFile(String customFile) {
		this.customFile = customFile;
	}
	
	
}
