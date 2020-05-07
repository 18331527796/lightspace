package com.threefriend.lightspace.mapper.xcx;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 任务记录表
 */
@Entity
public class TaskRecordMapper {

	@Id
	@GeneratedValue
	private Integer id;
	//用户id
	private Integer parentId;
	//任务id
	private Integer taskId;
	//签到时间
	private Date genTime;
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
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	
	
	
}
