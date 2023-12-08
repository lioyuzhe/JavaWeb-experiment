package com.usst.usstcafeteriahub.filter;



import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


// 说明： 有拦截器，所以这个共能可以不用
/**
 *  过滤器
 * @author 李英杰
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("初始化过滤器");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截一次请求");
        System.out.println("放行前操作:");


        HttpServletRequest req =  (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;
        //1.获取请求ur1.
        String s = req.getRequestURI();
        System.out.println(s);
        log.info("过滤器捕获：请求的uri:{}",s);

        //2.判惭请求ur1中是否包含1ogin,如果包含，说明是登录操作，放行。
        if(s.contains("login")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
// 先全部放行
        log.info("放行啦~");
        filterChain.doFilter(servletRequest, servletResponse);
        return;
//
//        //3.获取请求头中的令牌(token).
//        String token = req.getHeader("Authorization");
//        log.info("请求头中的token:{}",token);
//
//
//        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）。
//        if(token==null){
//            log.info("token不存在");
//            // 手动将对象转换成json格式的字符串 --- 使用阿里巴巴的fastjson
//            String notLogin = JSONObject.toJSONString(Result.error(401,"未登录"));
//            // 将json字符串写入到响应体中
//            resp.getWriter().write(notLogin);
//            return;
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
//            resp.getWriter().write(notLogin);
//            return;
//        }
//        log.info("token合法");
//
//        // 放行
//        filterChain.doFilter(servletRequest, servletResponse);
//        System.out.println("放行后操作:");


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("销毁过滤器");
    }
}
