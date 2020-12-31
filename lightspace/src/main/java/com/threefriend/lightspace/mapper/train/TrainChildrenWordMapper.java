package com.threefriend.lightspace.mapper.train;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 学生word表
 * 
 * @author Administrator
 *
 */
@Entity
public class TrainChildrenWordMapper {

	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//学生姓名
	private String name;
	//性别
	private String gender;
	//学生id
	private Integer childrenId;
	//生日
	private String birthday;
	//电话
	private String phone;
	//右远裸视							
	private String farRight;
	//左远裸视						
	private String farLeft;
	//右近裸视
	private String nearRight;
	//左近裸视
	private String nearLeft;
	//右球镜
	private String sphRight;
	//左球镜
	private String sphLeft;
	//右柱镜
	private String cytRight;
	//左柱镜
	private String cytLeft;
	//右轴位
	private String axisRight;
	//左轴位
	private String axisLeft;
	//右矫正视力
	private String correctRight;
	//左矫正视力
	private String correctLeft;
	//右瞳距
	private String ipdRight;
	//左瞳距
	private String ipdLeft;
	//右主导眼
	private String leadingRight;
	//左主导眼
	private String leadingLeft;
	//眼球运动
	private String  motion;
	//立体视检查
	private String stereopsis;
	//遮盖眼位
	private String cover;
	//Worth-4点
	private String worth;
	//集合近点
	private String assembly;
	//色觉检查
	private String colourVision;
	//右眼灵敏度
	private String splRight;
	//左眼灵敏度
	private String splLeft;
	//双眼灵敏度
	private String splBinoculus;
	//右水平曲率值
	private String levelRight;
	//左水平曲率值
	private String levelLeft;
	//右垂直曲率值
	private String verticalRight;
	//左垂直曲率值
	private String verticalLeft;
	//右眼轴长度
	private String axialLengthRight; 
	//左眼轴长度
	private String axialLengthLeft;
	//右前房深度
	private String acdRight;
	//左前房深度
	private String acdLeft;
	//右晶体厚度
	private String ltRight;
	//左晶体厚度
	private String ltLeft;
	//裂隙灯检查
	private String slitLamp;
	//眼底照相检查
	private String retCam;
	//身高
	private String height;
	//体重
	private String weight;
	//处理建议
	private String suggest;
	//上传人
	private String clert;
	//openId
	private String openId;
	//创建时间
	private Date genTime = new Date();
	//显示时间
	private String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getChildrenId() {
		return childrenId;
	}
	public void setChildrenId(Integer childrenId) {
		this.childrenId = childrenId;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFarRight() {
		return farRight;
	}
	public void setFarRight(String farRight) {
		this.farRight = farRight;
	}
	public String getFarLeft() {
		return farLeft;
	}
	public void setFarLeft(String farLeft) {
		this.farLeft = farLeft;
	}
	public String getNearRight() {
		return nearRight;
	}
	public void setNearRight(String nearRight) {
		this.nearRight = nearRight;
	}
	public String getNearLeft() {
		return nearLeft;
	}
	public void setNearLeft(String nearLeft) {
		this.nearLeft = nearLeft;
	}
	public String getSphRight() {
		return sphRight;
	}
	public void setSphRight(String sphRight) {
		this.sphRight = sphRight;
	}
	public String getSphLeft() {
		return sphLeft;
	}
	public void setSphLeft(String sphLeft) {
		this.sphLeft = sphLeft;
	}
	public String getCytRight() {
		return cytRight;
	}
	public void setCytRight(String cytRight) {
		this.cytRight = cytRight;
	}
	public String getCytLeft() {
		return cytLeft;
	}
	public void setCytLeft(String cytLeft) {
		this.cytLeft = cytLeft;
	}
	public String getAxisRight() {
		return axisRight;
	}
	public void setAxisRight(String axisRight) {
		this.axisRight = axisRight;
	}
	public String getAxisLeft() {
		return axisLeft;
	}
	public void setAxisLeft(String axisLeft) {
		this.axisLeft = axisLeft;
	}
	public String getCorrectRight() {
		return correctRight;
	}
	public void setCorrectRight(String correctRight) {
		this.correctRight = correctRight;
	}
	public String getCorrectLeft() {
		return correctLeft;
	}
	public void setCorrectLeft(String correctLeft) {
		this.correctLeft = correctLeft;
	}
	public String getIpdRight() {
		return ipdRight;
	}
	public void setIpdRight(String ipdRight) {
		this.ipdRight = ipdRight;
	}
	public String getIpdLeft() {
		return ipdLeft;
	}
	public void setIpdLeft(String ipdLeft) {
		this.ipdLeft = ipdLeft;
	}
	public String getLeadingRight() {
		return leadingRight;
	}
	public void setLeadingRight(String leadingRight) {
		this.leadingRight = leadingRight;
	}
	public String getLeadingLeft() {
		return leadingLeft;
	}
	public void setLeadingLeft(String leadingLeft) {
		this.leadingLeft = leadingLeft;
	}
	public String getMotion() {
		return motion;
	}
	public void setMotion(String motion) {
		this.motion = motion;
	}
	public String getStereopsis() {
		return stereopsis;
	}
	public void setStereopsis(String stereopsis) {
		this.stereopsis = stereopsis;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getWorth() {
		return worth;
	}
	public void setWorth(String worth) {
		this.worth = worth;
	}
	public String getAssembly() {
		return assembly;
	}
	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}
	public String getColourVision() {
		return colourVision;
	}
	public void setColourVision(String colourVision) {
		this.colourVision = colourVision;
	}
	public String getSplRight() {
		return splRight;
	}
	public void setSplRight(String splRight) {
		this.splRight = splRight;
	}
	public String getSplLeft() {
		return splLeft;
	}
	public void setSplLeft(String splLeft) {
		this.splLeft = splLeft;
	}
	public String getSplBinoculus() {
		return splBinoculus;
	}
	public void setSplBinoculus(String splBinoculus) {
		this.splBinoculus = splBinoculus;
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
	public String getAcdRight() {
		return acdRight;
	}
	public void setAcdRight(String acdRight) {
		this.acdRight = acdRight;
	}
	public String getAcdLeft() {
		return acdLeft;
	}
	public void setAcdLeft(String acdLeft) {
		this.acdLeft = acdLeft;
	}
	public String getLtRight() {
		return ltRight;
	}
	public void setLtRight(String ltRight) {
		this.ltRight = ltRight;
	}
	public String getLtLeft() {
		return ltLeft;
	}
	public void setLtLeft(String ltLeft) {
		this.ltLeft = ltLeft;
	}
	public String getSlitLamp() {
		return slitLamp;
	}
	public void setSlitLamp(String slitLamp) {
		this.slitLamp = slitLamp;
	}
	public String getRetCam() {
		return retCam;
	}
	public void setRetCam(String retCam) {
		this.retCam = retCam;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getClert() {
		return clert;
	}
	public void setClert(String clert) {
		this.clert = clert;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	
	
}
