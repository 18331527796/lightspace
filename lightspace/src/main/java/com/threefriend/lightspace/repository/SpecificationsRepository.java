package com.threefriend.lightspace.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.xcx.mapper.SpecificationsMapper;


/**
 * 商品规格操作层
 * @author Administrator
 *
 */
public interface SpecificationsRepository extends JpaRepository<SpecificationsMapper, Integer>{

	List<SpecificationsMapper> findByProduceId(Integer product);
}
