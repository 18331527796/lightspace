package com.threefriend.lightspace.mapper.xcx;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 点赞记录表
 * @author Administrator
 *
 */
@Entity
public class FabulousRecordMapper {

	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer parentId;
	
	private Integer taskExamineId;
	
	private String avatarUrl;
	
	private String nickName;
	
	private String date;
	
	private Integer sendId;
	
	private Integer sendType;//1:发送 2:未发送

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getTaskExamineId() {
		return taskExamineId;
	}

	public void setTaskExamineId(Integer taskExamineId) {
		this.taskExamineId = taskExamineId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.date = format.format(date);
	}

	public Integer getSendId() {
		return sendId;
	}

	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	
	
}
