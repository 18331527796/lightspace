package com.threefriend.dingding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.threefriend.constants.DingDingAccessToken;
import com.threefriend.dingding.dto.DeptDTO;
import com.threefriend.dingding.mapper.UserTaskRecordMapper;
import com.threefriend.dingding.util.DingDingUtils;
import com.threefriend.dingding.util.HttpUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	
	

    @Test
    public void contextLoads() throws Exception {
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-31"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date begin = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        Date end = calendar.getTime();
        System.out.println(begin+"----"+end);
    }
	
}
