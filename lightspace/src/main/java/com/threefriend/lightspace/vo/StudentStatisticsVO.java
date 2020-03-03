package com.threefriend.lightspace.vo;

import java.util.List;

import com.threefriend.lightspace.mapper.StudentWordMapper;

public class StudentStatisticsVO {
	
	private OneStatisticsVO visionLeft;
	
	private OneStatisticsVO visionRight;
	
	private OneStatisticsVO eyeAxisLengthLeft;
	
	private OneStatisticsVO eyeAxisLengthRight;
	
	private OneStatisticsVO curvatureLeft;
	
	private OneStatisticsVO curvatureRight;
	
	private StudentWordMapper studnetWord;

	public OneStatisticsVO getVisionLeft() {
		return visionLeft;
	}

	public void setVisionLeft(OneStatisticsVO visionLeft) {
		this.visionLeft = visionLeft;
	}

	public OneStatisticsVO getVisionRight() {
		return visionRight;
	}

	public void setVisionRight(OneStatisticsVO visionRight) {
		this.visionRight = visionRight;
	}

	public OneStatisticsVO getEyeAxisLengthLeft() {
		return eyeAxisLengthLeft;
	}

	public void setEyeAxisLengthLeft(OneStatisticsVO eyeAxisLengthLeft) {
		this.eyeAxisLengthLeft = eyeAxisLengthLeft;
	}

	public OneStatisticsVO getEyeAxisLengthRight() {
		return eyeAxisLengthRight;
	}

	public void setEyeAxisLengthRight(OneStatisticsVO eyeAxisLengthRight) {
		this.eyeAxisLengthRight = eyeAxisLengthRight;
	}

	public OneStatisticsVO getCurvatureLeft() {
		return curvatureLeft;
	}

	public void setCurvatureLeft(OneStatisticsVO curvatureLeft) {
		this.curvatureLeft = curvatureLeft;
	}

	public OneStatisticsVO getCurvatureRight() {
		return curvatureRight;
	}

	public void setCurvatureRight(OneStatisticsVO curvatureRight) {
		this.curvatureRight = curvatureRight;
	}

	public StudentWordMapper getStudnetWord() {
		return studnetWord;
	}

	public void setStudnetWord(StudentWordMapper studnetWord) {
		this.studnetWord = studnetWord;
	}
	
	
}
