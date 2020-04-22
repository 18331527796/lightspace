package com.twilio.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import com.twilio.Twilio;
import com.twilio.demo.khfsl.drow;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	
	

    @Test
    public void contextLoads() throws Exception {
    	drow cg = new drow();
		/**
		 *
		 * @param imglogo
		 *            logo图片
		 * @param imgcode
		 *            二维码图片
		 * @param words
		 *            文字信息
		 * @param outurl
		 *            输出的Url路径
		 */
		String words = "您好！这里是致家长的一封&信，信件里面写满了对您孩子满满的&爱，请关注您孩子的眼睛发展趋势。";
		try {
			cg.graphicsGeneration("E:\\光亮空间logo.png", "E:\\杨博淋.jpg",
					"D:\\upload\\zxing\\ccc.jpg", words, "E:\\");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}
