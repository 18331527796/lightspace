package com.threefriend.lightspace.mapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 标签
 * 
 * @author Administrator
 *
 */
@Entity
public class SeriesProductMapper {
	

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 标签名
	private Integer seriesId;
	//
	private String seriesName;
	// 介绍
	private String pic;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
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
	
	

}
