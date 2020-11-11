package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.List;

import com.threefriend.lightspace.mapper.GlassesMapper;

public class GlassesVO {

	public GlassesVO(GlassesMapper po) {
		id = po.getId();
		name = po.getName();
		refractive = po.getRefractive();
		abbe = po.getAbbe();
		film = po.getFilm();
		covert = po.getCovert();
		addLightBelow = po.getAddLightBelow();
		presentPrice = po.getPresentPrice();
		customPrice = po.getCustomPrice();
		seriesId = po.getSeriesId();
		seriesName = po.getSeriesName();
		labelId = po.getLabelId();
		labelName = po.getLabelName();
		benchmark = po.getBenchmark();
		checked = po.isChecked();
		sphericalMirror = po.getSphericalMirror();
		colonoscope = po.getColonoscope();
		onTheSpot = po.getOnTheSpot();

		if (!po.getPhotometric1().isEmpty()) {
			if (po.getPhotometric1().indexOf("/") == -1) {
				photometric1.add(po.getPhotometric1());
			} else {
				String[] split = po.getPhotometric1().split("/");
				for (String string : split) {
					photometric1.add(string);
				}
			}
		}
		if (!po.getPhotometric2().isEmpty()) {
			if (po.getPhotometric2().indexOf("/") == -1) {
				photometric2.add(po.getPhotometric2());
			} else {
				String[] split = po.getPhotometric2().split("/");
				for (String string : split) {
					photometric2.add(string);
				}
			}
		}
		if (!po.getPhotometric3().isEmpty()) {
			if (po.getPhotometric3().indexOf("/") == -1) {
				photometric3.add(po.getPhotometric3());
			} else {
				String[] split = po.getPhotometric3().split("/");
				for (String string : split) {
					photometric3.add(string);
				}
			}
		}

	}

	// 主键
	private Integer id;
	// 产品名称
	private String name;
	// 折射率
	private String refractive;
	// 阿贝数
	private String abbe;
	// 膜层
	private String film;
	// 隐形标记
	private String covert;
	// 光度范围1
	private List<String> photometric1 = new ArrayList<>();
	// 光度范围2
	private List<String> photometric2 = new ArrayList<>();
	// 光度范围3
	private List<String> photometric3 = new ArrayList<>();
	// 下加光
	private String addLightBelow;
	// 现片价
	private String presentPrice;
	// 定制价
	private String customPrice;
	// 系列关联id
	private Integer seriesId;
	// 系列名
	private String seriesName;
	// 标签id
	private Integer labelId;
	// 标签名
	private String labelName;
	// 基准
	private String benchmark;
	
	//球镜
	private String sphericalMirror;
	//柱镜
	private String colonoscope;
	//现片
	private String onTheSpot;

	private boolean checked;

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

	public List<String> getPhotometric1() {
		return photometric1;
	}

	public void setPhotometric1(List<String> photometric1) {
		this.photometric1 = photometric1;
	}

	public List<String> getPhotometric2() {
		return photometric2;
	}

	public void setPhotometric2(List<String> photometric2) {
		this.photometric2 = photometric2;
	}

	public List<String> getPhotometric3() {
		return photometric3;
	}

	public void setPhotometric3(List<String> photometric3) {
		this.photometric3 = photometric3;
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

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
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

}
