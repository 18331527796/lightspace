package com.threefriend.dingding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.threefriend.dingding.util.DingDingUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	
	

    @Test
    public void contextLoads() throws Exception {
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-28"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date begin = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        Date end = calendar.getTime();
        System.out.println(begin+"----"+end);
        
        DingDingUtils.attendance("3237242237787613", begin, end);
    }
	
}
