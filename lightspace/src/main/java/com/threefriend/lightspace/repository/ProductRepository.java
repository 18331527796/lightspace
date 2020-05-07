package com.threefriend.lightspace.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.xcx.ProductMapper;

/**
 * 商品操作层
 *
 */
public interface ProductRepository extends JpaRepository<ProductMapper, Integer>{
	
	Page<ProductMapper> findAllByOrderByIdDesc(Pageable Pageable);
	
	Page<ProductMapper> findAllByNameLikeOrderByIdDesc(String name,Pageable Pageable);
}
