package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.xcx.mapper.IntegralMapper;
import com.threefriend.lightspace.xcx.mapper.PartnershipMapper;

/**
 * 合作机构操作层
 * @author Administrator
 *
 */
public interface PartnershipRepository extends JpaRepository<PartnershipMapper, Integer>{

}
