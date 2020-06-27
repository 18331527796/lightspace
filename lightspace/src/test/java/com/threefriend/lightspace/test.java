package com.threefriend.lightspace;


import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.lightspace.mapper.xcx.FabulousRecordMapper;
import com.threefriend.lightspace.repository.FabulousRcordRepository;




@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	@Autowired
	private FabulousRcordRepository fabulous_record_dao;
	@Test
	public void test(){
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
