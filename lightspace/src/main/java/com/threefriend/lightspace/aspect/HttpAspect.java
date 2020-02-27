package com.threefriend.lightspace.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.threefriend.lightspace.Exception.LightException;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.repository.SysLogRepository;
import com.threefriend.lightspace.util.RedisUtils;

@Aspect
@Component
public class HttpAspect {
	
	@Resource
	private RedisUtils redisUtil;
	@Autowired
	private SysLogRepository sysLogService;

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
	
	//定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.threefriend.lightspace.aspect.Mylog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
    	
    	System.out.println("日志切面……");
        //保存日志
        SysLogMapper sysLog = new SysLogMapper();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        Mylog myLog = method.getAnnotation(Mylog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setOperation(value);//保存获取的操作
            if( value.contains("新增")) sysLog.setType("新增");
        	if( value.contains("删除")) sysLog.setType("删除");
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        sysLog.setParams(params);

        sysLog.setCreateDate(new Date());
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session =request.getSession();
        //获取用户名
        sysLog.setUsername(session.getAttribute("userName")+"");

        //调用service保存SysLog实体类到数据库
        sysLogService.save(sysLog);
    }
	
}
