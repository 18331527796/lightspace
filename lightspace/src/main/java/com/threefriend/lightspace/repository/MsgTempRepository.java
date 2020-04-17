package com.threefriend.lightspace.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.MsgTempMapper;

/**
 * 模板操作层
 * @author Administrator
 *
 */
public interface MsgTempRepository extends JpaRepository<MsgTempMapper, Integer>{

	MsgTempMapper findByTypeAndSelected(String type , Integer selected);
	
	List<MsgTempMapper> findByType(String type);
}
