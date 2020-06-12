package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.util.OptotypeUtils;

public class ScreeningVO {
	//主键
		private Integer id;
		//学生id
		private Integer studentId;
		//性别
		private Integer gender;
		// 姓名
		private String studentName;
		//生日
		private String birthday;
		//总获得积分
		private Long myIntegral;
		// 左眼裸眼视力
		private String visionLeft;
		private String vision5Left;
		private Double visionLeftStr;
		// 右眼裸眼视力
		private String visionRight;
		private String vision5Right;
		private Double visionRightStr;
		// 左眼过程
		private List<Map<String,String>> processLeftList;
		// 右眼过程
		private List<Map<String,String>> processRightList;
		//创建时间
		private Date genTime;
		private String date;
		
		
		
		public Long getMyIntegral() {
			return myIntegral;
		}
		public void setMyIntegral(Long myIntegral) {
			this.myIntegral = myIntegral;
		}
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public String getVision5Left() {
			return vision5Left;
		}
		public void setVision5Left(String vision5Left) {
			this.vision5Left = vision5Left;
		}
		public String getVision5Right() {
			return vision5Right;
		}
		public void setVision5Right(String vision5Right) {
			this.vision5Right = vision5Right;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getVisionLeft() {
			return visionLeft;
		}
		public void setVisionLeft(String visionLeft) {
			this.visionLeft = visionLeft;
		}
		public Double getVisionLeftStr() {
			return visionLeftStr;
		}
		public void setVisionLeftStr(Double visionLeftStr) {
			this.visionLeftStr = visionLeftStr;
			this.visionLeft = OptotypeUtils.vision2vision5(visionLeftStr);
			this.vision5Left = OptotypeUtils.vision2onlyvision5(visionLeftStr);
		}
		public String getVisionRight() {
			return visionRight;
		}
		public void setVisionRight(String visionRight) {
			this.visionRight = visionRight;
		}
		public Double getVisionRightStr() {
			return visionRightStr;
		}
		public void setVisionRightStr(Double visionRightStr) {
			this.visionRightStr = visionRightStr;
			this.visionRight = OptotypeUtils.vision2vision5(visionRightStr);
			this.vision5Right = OptotypeUtils.vision2onlyvision5(visionRightStr);
		}
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
		public String getStudentName() {
			return studentName;
		}
		public void setStudentName(String studentName) {
			this.studentName = studentName;
		}
		public Date getGenTime() {
			return genTime;
		}
		public void setGenTime(Date genTime) {
			this.genTime = genTime;
		}
		public Integer getGender() {
			return gender;
		}
		public void setGender(Integer gender) {
			this.gender = gender;
		}
		public List<Map<String, String>> getProcessLeftList() {
			return processLeftList;
		}
		public void setProcessLeftList(List<Map<String, String>> processLeftList) {
			this.processLeftList = processLeftList;
		}
		public List<Map<String, String>> getProcessRightList() {
			return processRightList;
		}
		public void setProcessRightList(List<Map<String, String>> processRightList) {
			this.processRightList = processRightList;
		}
		
}
