package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.LabelMapper;

/** 
 * 标签层
 * @author Administrator
 *
 */
public interface LabelRepository extends JpaRepository<LabelMapper, Integer>{

}
