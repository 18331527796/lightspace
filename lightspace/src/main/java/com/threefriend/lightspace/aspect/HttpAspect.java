package com.threefriend.lightspace.aspect;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.Exception.LightException;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.util.RedisUtils;

@Aspect
@Component
public class HttpAspect {
	
	@Resource
	private RedisUtils redisUtil;

	@Pointcut("execution(public * com.threefriend.lightspace.controller.*.*(..))" +
		    "&& !execution(public * com.threefriend.lightspace.controller.UserController.login(..))")
		    public void verify() {}
	
	
	@Before("verify()")
	public void doVerify() {
		System.out.println("验证token时刻！！！"+new Date());
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getParameter("token");
        System.out.println("--------"+token+"--------这是AOP判定");
        String[] split = token.split("-");
        String tokenValue = redisUtil.get(split[0]);
        //开启token验证
        if(StringUtils.isEmpty(tokenValue))throw new LightException(ResultEnum.TOKEN_OVERDUE);
        redisUtil.setTime(split[0], 1800);//1800
	}
}
