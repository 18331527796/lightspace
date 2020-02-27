package com.threefriend.lightspace;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.constant.VisionEnums;
import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.RegionRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	@Autowired
	private RecordRepository record_dao;
	
	
	
	@Test
	public void test() {
		System.out.println(record_dao.countTopBySchoolIdAndVisionLeftGreaterThanOrderByStudentId(22,0.81));
		System.out.println(record_dao.countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(22,0.8,0.81));
		System.out.println(record_dao.countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(22,0.4,0.79));
		System.out.println(record_dao.countTopBySchoolIdAndVisionLeftBetweenOrderByStudentId(22,0.1,0.39));
	}
	
}
