package com.threefriend.lightspace.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Aspect
@Component
public class HttpAspect {
	
	@Autowired
    private StringRedisTemplate redisTemplate;

	@Pointcut("execution(public * com.threefriend.lightspace.controller.*.*(..))" +
		    "&& !execution(public * com.threefriend.lightspace.controller.UserController.*(..))")
		    public void verify() {}
	
	
	@Before("verify()")
	public ResultVO doVerify() {
		System.out.println("验证token时刻！！！");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getParameter("token");
        String tokenValue = redisTemplate.opsForValue().get(token);
        if(StringUtils.isEmpty(tokenValue))return ResultVOUtil.error(ResultEnum.TOKEN_ERROR.getStatus(), ResultEnum.TOKEN_ERROR.getMessage());
		return ResultVOUtil.success();
	}
}
