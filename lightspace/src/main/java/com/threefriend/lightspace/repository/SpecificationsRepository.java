package com.threefriend.lightspace.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.xcx.ProductMapper;
import com.threefriend.lightspace.mapper.xcx.SpecificationsMapper;


/**
 * 商品规格操作层
 * @author Administrator
 *
 */
public interface SpecificationsRepository extends JpaRepository<SpecificationsMapper, Integer>{

	Page<SpecificationsMapper> findByProductId(Integer product,Pageable Pageable);
	
	Page<SpecificationsMapper> findAllByOrderByIdDesc(Pageable Pageable);
	
	void deleteByProductId(Integer productId);
}
