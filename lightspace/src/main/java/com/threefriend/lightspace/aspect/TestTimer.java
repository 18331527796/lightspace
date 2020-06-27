package com.threefriend.lightspace.aspect;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class TestTimer {
	public static void start() {
		initTimer();
	}

	public static void initTimer() {
		Timer t = new Timer();//  创建Timer对象
		TimerTask tt = new TimerTask() {
			public void run() {
				System.out.println("now time is " + new Date());
			}
		};// 创建TimerTask对象，Timer对象会调用TimerTask的run()方法

		t.schedule(tt, 0, 3000);
		while (true) {//  这个是用来停止此任务的,否则就一直循环执行此任务了
			try {
				int ch = System.in.read();
				if (ch - 'c' == 0) {
					t.cancel();//  使用这个方法退出任务
				}
			} catch (IOException e) {
				//  TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
