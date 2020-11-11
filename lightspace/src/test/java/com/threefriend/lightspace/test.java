package com.threefriend.lightspace;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.threefriend.lightspace.mapper.SortMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.FabulousRecordMapper;
import com.threefriend.lightspace.repository.FabulousRcordRepository;
import com.threefriend.lightspace.repository.SortRepository;
import com.threefriend.lightspace.repository.StudentRepository;




@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	@Autowired
	private SortRepository sort_dao;
	@Autowired
	private StudentRepository student_dao;
	@Test
	public void test(){
		
		System.out.println(0%6);
		System.out.println(1%6);
		System.out.println(2%6);
		System.out.println(3%6);
		System.out.println(4%6);
		System.out.println(5%6);
		System.out.println(6%6);
		System.out.println(7%6);
		System.out.println(8%6);
		System.out.println(9%6);
		System.out.println(10%6);
		System.out.println(11%6);
		/*//计算当前的学年学期
        Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH )+1;
		String term = "1学期";
		if(month < 9)
			year = year - 1;
		if (month>=7 && month<9) {
			term = "暑假";
		}else if(month>=9 && month<1){
			term = "1学期";
		}else if(month>=1 && month<3) {
			term = "寒假";
		}else {
			term = "2学期";
		}
		System.out.println("现在是：" + year + " - " + (year + 1) +  "学年，" + term + "。");*/
		
	}
	
		

	public void initTimer() {
		Timer t = new Timer();// 创建Timer对象
		TimerTask tt = new TimerTask() {
			 public void run() {
				 System.out.println("我就是想试试这个功能");
			 }
		};//创建TimerTask对象，Timer对象会调用TimerTask的run()方法
		while (true) {// 这个是用来停止此任务的,否则就一直循环执行此任务了
		try {
  
		 int ch;
			ch = System.in.read();
			if (ch - 'c' == 0) {
				t.cancel();// 使用这个方法退出任务
			}
			t.schedule(tt,1000,3000) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
 }
	
	
}
