package com.threefriend.lightspace.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HttpAspect {

	@Before("execution(public * com.threefriend.lightspace.controller.*.*(..))")
	public void test() {
		System.out.println("试验成功！！！！！");
	}
}
