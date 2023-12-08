package com.usst.usstcafeteriahub.config;


import com.usst.usstcafeteriahub.interceptor.jwt.LoginCheckInterceptor;
import com.usst.usstcafeteriahub.interceptor.rateLimit.RateLimiterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 配置拦截路径
@Configuration
public class WebConfig implements WebMvcConfigurer {

    RateLimiterInterceptor rateLimiterInterceptor;  // 限流拦截器


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns("/login") // 不拦截登录请求
                .excludePathPatterns("/register") // 不拦截注册请求
                .excludePathPatterns("/static/**"); // 不拦截静态资源
        registry.addInterceptor(new RateLimiterInterceptor()).addPathPatterns("/**");
    }
}
