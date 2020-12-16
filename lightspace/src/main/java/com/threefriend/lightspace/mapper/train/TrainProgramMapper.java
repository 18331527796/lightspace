package com.threefriend.lightspace.mapper.train;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 方案库
 * @author Administrator
 *
 */
@Entity
public class TrainProgramMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	//训练分类
	private String name;
	//使用工具
	private String tool;
	//训练方向（两种 弱视训练 视觉训练）
	private String direction;
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
	public String getTool() {
		return tool;
	}
	public void setTool(String tool) {
		this.tool = tool;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
