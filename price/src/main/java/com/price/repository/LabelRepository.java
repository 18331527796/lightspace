package com.price.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.price.mapper.LabelMapper;


/** 
 * 标签层
 * @author Administrator
 *
 */
public interface LabelRepository extends JpaRepository<LabelMapper, Integer>{

}
