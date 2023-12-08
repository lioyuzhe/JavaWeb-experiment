package com.usst.usstcafeteriahub.interceptor.jwt;

import com.alibaba.fastjson.JSONObject;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override  // 重写preHandle方法，在请求发生前执行 // 返回true表示放行，返回false表示拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("登录拦截器：我在检测jwt令牌，工作中……");

        log.info("等待中……");
        log.info("放行啦~");

        return HandlerInterceptor.super.preHandle(request, response, handler); // 放行



//        //1.获取请求ur1.
//        String s = request.getRequestURI().toString();
//        System.out.println(s);
//        log.info("请求的uri:{}",s);
//
//        //2.判惭请求ur1中是否包含1ogin,如果包含，说明是登录操作，放行。
//        if(s.contains("login")){
//
//            return true;
//        }
//
//        //3.获取请求头中的令牌(token).
//        String token = request.getHeader("token");
//        log.info("请求头中的token:{}",token);
//
//
//        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）。
//        if(token==null){
//            log.info("token不存在");
//            // 手动将对象转换成json格式的字符串 --- 使用阿里巴巴的fastjson
//            String notLogin = JSONObject.toJSONString(Result.error(401,"未登录"));
//            // 将json字符串写入到响应体中
//            response.getWriter().write(notLogin);
//            return false;
//        }
//
//        //5.解析token,如果解析失败，返回错误结果（未登录）。
//        try {
//            JwtUtils.parseToken(token);
//        } catch (Exception e) {
//            log.info("token解析失败");
//            // 手动将对象转换成json格式的字符串 --- 使用阿里巴巴的fastjson
//            String notLogin = JSONObject.toJSONString(Result.error(401,"未登录"));
//            // 将json字符串写入到响应体中
//            response.getWriter().write(notLogin);
//            return false;
//        }
//        log.info("token合法");
//        return true;

    }

    @Override // 重写postHandle方法，在请求完成后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView); // 放行
    }

    @Override // 重写afterCompletion方法，在请求完成后执行，主要用于清理资源
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex); // 放行
    }
}
