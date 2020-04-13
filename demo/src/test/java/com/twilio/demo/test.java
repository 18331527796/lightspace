package com.twilio.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	
	
	private static final String accountSid = "ACdcef71c605933e0cb3910794861d3c34"; // Your Account SID from www.twilio.com/user/account
    private static final String authToken = "c492db2b8f7647d7fed0d925681101fd"; // Your Auth Token from www.twilio.com/user/account

    @Test
    public void contextLoads() throws Exception {
        Twilio.init(accountSid, authToken);
        for (int i = 0; i < 3; i++) {
			
        	Message message = Message.creator(
        			new PhoneNumber("8618331527796"),  // To number ,Phone number with area code
        			new PhoneNumber("12564458924"),  // From number
        			" 你是真鸡儿烦人！！！"                   // SMS body
        			).create();
        	if (! StringUtils.isEmpty(message.getSid())){
        		System.out.println(message.getSid());
        	}
        	Thread.sleep(2000);
        }
    }
	
}
