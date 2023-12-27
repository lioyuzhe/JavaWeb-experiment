package com.usst.usstcafeteriahub.aop;

import com.alibaba.fastjson.JSONObject;
import com.usst.usstcafeteriahub.mapper.OperateLogMapper;
import com.usst.usstcafeteriahub.model.log.OperateLog;
import com.usst.usstcafeteriahub.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect // 切面类
public class LogAspect {  
    @Autowired
    private HttpServletRequest request;// 获取改次请求的请求头信息
    @Autowired  
    private OperateLogMapper operateLogMapper;
  
    @Around("@annotation(com.usst.usstcafeteriahub.common.Log)") // 切入点表达式-annotation形式
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable { // 连接点
        // 操作人ID-当前登陆员工ID
        // 获取请求头中的jwt令牌 解析令牌  
        String jwt = request.getHeader("token");
        log.info("jwt: {}", jwt);
        if(jwt == null){
            // -- 直接调用目标方法不进行log记录 --
            Object result = joinPoint.proceed();
            return result;
        }
        Claims claims = JwtUtils.parseToken(jwt);
        Integer operateUser = (Integer) claims.get("id");
  
        // 操作时间
        LocalDateTime operateTime = LocalDateTime.now();
  
        // 操作类名
        String className = joinPoint.getTarget().getClass().getName();  
  
        // 操作方法名
        String methodName = joinPoint.getSignature().getName();  
  
        // 操作方法参数
        Object[] args = joinPoint.getArgs();  
        String methodParams = Arrays.toString(args);
  
        long begin = System.currentTimeMillis();  
        // -- 调用目标方法 --        
        Object result = joinPoint.proceed();  
        long end = System.currentTimeMillis();  

        // 方法返回值
        String returnValue = JSONObject.toJSONString(result);
  
        // 操作耗时
        long costTime = end - begin;  
  
        // 记录操作日志  
        OperateLog operateLog = new OperateLog(null, operateUser, operateTime, className, methodName, methodParams, returnValue, costTime);
        operateLogMapper.insert(operateLog);
        log.info("AOP记录操作日志: {}", operateLog);
  
        return result;  
    }  
}