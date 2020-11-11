package com.threefriend.lightspace.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.threefriend.lightspace.mapper.StudentWordMapper;

public class AxisLengthShowVO {

	// 右水平曲率值
	private String levelRight;
	// 左水平曲率值
	private String levelLeft;
	// 右垂直曲率值
	private String verticalRight;
	// 左垂直曲率值
	private String verticalLeft;
	// 右眼轴长度
	private String axialLengthRight;
	// 左眼轴长度
	private String axialLengthLeft;
	// 创建时间
	private String genTime;

	public AxisLengthShowVO(StudentWordMapper po) {
		this.levelRight=po.getLevelRight();
		this.levelLeft=po.getLevelLeft();
		this.verticalRight=po.getVerticalRight();
		this.verticalLeft=po.getVerticalLeft();
		this.axialLengthRight=po.getAxialLengthRight(); 
		this.axialLengthLeft=po.getAxialLengthLeft();
		this.genTime=new SimpleDateFormat("yyyy-MM-dd").format(po.getGenTime());
	}
	
	public AxisLengthShowVO() {}

	public String getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = new SimpleDateFormat("yyyy-MM-dd").format(genTime);
	}

	public String getLevelRight() {
		return levelRight;
	}

	public void setLevelRight(String levelRight) {
		this.levelRight = levelRight;
	}

	public String getLevelLeft() {
		return levelLeft;
	}

	public void setLevelLeft(String levelLeft) {
		this.levelLeft = levelLeft;
	}

	public String getVerticalRight() {
		return verticalRight;
	}

	public void setVerticalRight(String verticalRight) {
		this.verticalRight = verticalRight;
	}

	public String getVerticalLeft() {
		return verticalLeft;
	}

	public void setVerticalLeft(String verticalLeft) {
		this.verticalLeft = verticalLeft;
	}

	public String getAxialLengthRight() {
		return axialLengthRight;
	}

	public void setAxialLengthRight(String axialLengthRight) {
		this.axialLengthRight = axialLengthRight;
	}

	public String getAxialLengthLeft() {
		return axialLengthLeft;
	}

	public void setAxialLengthLeft(String axialLengthLeft) {
		this.axialLengthLeft = axialLengthLeft;
	}

}
