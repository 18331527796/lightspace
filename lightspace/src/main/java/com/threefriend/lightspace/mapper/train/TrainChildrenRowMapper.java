package com.threefriend.lightspace.mapper.train;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TrainChildrenRowMapper {

	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer childrenCombinationId;
	
	private Integer row;
	
	private Integer isSuccess = 2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getChildrenCombinationId() {
		return childrenCombinationId;
	}

	public void setChildrenCombinationId(Integer childrenCombinationId) {
		this.childrenCombinationId = childrenCombinationId;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	
	
}
