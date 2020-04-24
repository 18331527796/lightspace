package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.mapper.xcx.ProductMapper;

/**
 * 商品操作层
 *
 */
public interface ProductRepository extends JpaRepository<ProductMapper, Integer>{
	
	List<ProductMapper> findByOrderByGenTimeDesc();
	
	List<ProductMapper> findByNameLike(String name);
}
