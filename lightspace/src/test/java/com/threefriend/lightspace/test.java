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
		while (true) {
			System.out.println("准备开始计算：");
			int longs = 0;
			int weith= 0;
			int hight= 0;
			System.out.println("请输入 长-宽-高：");
			Scanner input= new Scanner(System.in);
			if(input.hasNext()) {
				String nextLine = input.next();
				String[] split = nextLine.split("-");
				longs=Integer.valueOf(split[0]);
				weith=Integer.valueOf(split[1]);
				hight=Integer.valueOf(split[2]);
			}
			System.err.println("总面积："+((longs*weith)*2+(longs*hight)*2+(weith*hight)*2));
			System.err.println("体积为："+longs*weith*hight);
		}
	}
	
}
