package com.usst.usstcafeteriahub.interceptor.rateLimit;

import com.revinate.guava.util.concurrent.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Configuration
public class RateLimiterInterceptor implements HandlerInterceptor {


    /**
     * 令牌桶里放50个令牌，外界调用本系统所有接口，都得先在桶里获取令牌
     * 拿到令牌后，才可以正常调用接口，为了限流
     */
    private static final RateLimiter limiter = RateLimiter.create(500);


    // 拦截器的前置方法 preHandle
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 阻塞等待
        log.info("流量拦截器（RateLimiterInterceptor）工作中……");
        //limiter.acquire()的作用是获取一个资源的许可，如果许可不足，则会阻塞等待，直到获取到足够的许可才会返回。
        // 因为是拦截器，所以所有请求会在这里被拦截，然后进行限流
        log.info("等待中……");
        limiter.acquire();
        log.info("放行啦~");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
