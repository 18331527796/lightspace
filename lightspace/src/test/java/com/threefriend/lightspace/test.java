package com.threefriend.lightspace;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.enums.VisionEnums;
import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.xcxutil.CreateQrcore;
import com.threefriend.lightspace.xcxutil.WaterMarkUtils;
import com.threefriend.lightspace.xcxutil.XcxDecryptUtils;
import com.threefriend.lightspace.xcxutil.ZipUtils;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	@Autowired
	private RecordRepository record_dao;
	
	
	
	@Test
	public void test() throws ParseException {
		String phone = "13242633555";
		String resphone = phone.substring(0, 3)+"****"+ phone.substring(7, 11);
		System.err.println(resphone);
	}
	
}
