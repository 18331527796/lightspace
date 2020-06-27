package com.threefriend.lightspace.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.xcx.AnswerMapper;
import com.threefriend.lightspace.mapper.xcx.FabulousRecordMapper;
import com.threefriend.lightspace.mapper.xcx.FlowerRecordMapper;

public interface FlowerRcordRepository extends JpaRepository<FlowerRecordMapper, Integer>{

	FlowerRecordMapper findByParentId(Integer parentId);
}
