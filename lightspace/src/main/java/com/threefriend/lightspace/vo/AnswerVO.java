package com.threefriend.lightspace.vo;

import java.io.Serializable;

import com.threefriend.lightspace.enums.AnswerTypeEnums;
import com.threefriend.lightspace.mapper.xcx.AnswerMapper;

public class AnswerVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id ;

	private String title;
	
	private String optionOne;
	
	private String optionTwo;
	
	private String optionThree;
	
	private String optionFour;
	
	private String keyStr;
	
	private String type;
	
	private Integer level;
	
	private String explains;
	
	public AnswerVO(AnswerMapper po) {
		this.id = po.getId();
		this.title = po.getTitle();
		this.keyStr = po.getKeyStr();
		this.level = po.getLevel();
		this.explains = po.getExplains();
		this.type = po.getType()==AnswerTypeEnums.SINGLE.getCode()?AnswerTypeEnums.SINGLE.getMessage():AnswerTypeEnums.MULTIPLE.getMessage();
		String[] split = po.getOptionStr().split("-");
		for (int i = 0; i < split.length; i++) {
			if(i==0)this.optionOne   = split[i].substring(1, split[i].length());
			if(i==1)this.optionTwo   = split[i].substring(1, split[i].length());
			if(i==2)this.optionThree = split[i].substring(1, split[i].length());
			if(i==3)this.optionFour  = split[i].substring(1, split[i].length());
		}
	}
	
	public AnswerVO() {
		
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptionOne() {
		return optionOne;
	}

	public void setOptionOne(String optionOne) {
		this.optionOne = optionOne;
	}

	public String getOptionTwo() {
		return optionTwo;
	}

	public void setOptionTwo(String optionTwo) {
		this.optionTwo = optionTwo;
	}

	public String getOptionThree() {
		return optionThree;
	}

	public void setOptionThree(String optionThree) {
		this.optionThree = optionThree;
	}

	public String getOptionFour() {
		return optionFour;
	}

	public void setOptionFour(String optionFour) {
		this.optionFour = optionFour;
	}

	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getExplains() {
		return explains;
	}

	public void setExplain(String explains) {
		this.explains = explains;
	}
	
	
}
