package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.PartnershipMapper;

/**
 * 合作机构操作层
 * @author Administrator
 *
 */
public interface PartnershipRepository extends JpaRepository<PartnershipMapper, Integer>{

	Page<PartnershipMapper> findByRegionId(Integer regionId,Pageable page);
	
	List<PartnershipMapper> findByRegionId(Integer regionId);
}
