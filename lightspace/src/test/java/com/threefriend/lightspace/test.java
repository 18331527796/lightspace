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

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.enums.VisionEnums;
import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.xcxutil.CreateQrcore;
import com.threefriend.lightspace.xcxutil.WaterMarkUtils;
import com.threefriend.lightspace.xcxutil.ZipUtils;



@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	@Autowired
	private RecordRepository record_dao;
	
	
	
	@Test
	public void test() throws ParseException {
		System.out.println();
		/*int []test = {1,2,3,4};
		int []test1= {6,7,8,9};
		while(true) {
			
			int p =0;
			for (int i : test) {
				Scanner input =new Scanner(System.in);
				if (input.hasNext()) {
					if(Integer.valueOf(input.next())==i)p+=1;
					System.err.println(p);
				}
			}
			System.out.println("检查完一轮了");
			if(p<=1&&test[0]==6) {
				System.out.println("错了");break;
			}
			if(p>=4&&test[0]==6) {
				System.out.println("就是这样");break;
			}
			if(p>=3&&test[0]==1) {
				System.out.println("换视标中……");
				test=test1;
			}
		}*/
	}
	
}
