package com.threefriend.lightspace.mapper.xcx;

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
	
	
}
