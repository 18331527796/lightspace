package com.threefriend.lightspace;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
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
	public void test() throws ParseException {
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
		Date end = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
		System.err.println(begin+"--"+end);
	}
	
}
