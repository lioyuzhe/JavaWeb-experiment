package com.usst.usstcafeteriahub.filter;


import com.usst.usstcafeteriahub.filter.wrapper.SensitiveRequestWrapper;
import com.usst.usstcafeteriahub.utils.SensitiveFilter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Slf4j
public class SensitiveWordsWebFilter implements Filter {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    public SensitiveWordsWebFilter(SensitiveFilter sensitiveFilter) {
        this.sensitiveFilter = sensitiveFilter;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // 这里实现敏感词过滤逻辑
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        log.info("进入敏感词过滤器的http请求类：{}",httpServletRequest);
        SensitiveRequestWrapper sensitiveRequestWrapper = new SensitiveRequestWrapper(httpServletRequest, sensitiveFilter);

        String url = sensitiveRequestWrapper.getRequestURI().toString();
        log.info("请求的url：{}", url);
        log.info("包装后的请求类：{}",sensitiveRequestWrapper);

        filterChain.doFilter(sensitiveRequestWrapper, servletResponse);


    }

    // 根据需要实现 init 和 destroy 方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
        Filter.super.init(filterConfig);
        System.out.println("初始化敏感词过滤器");
    }

    @Override
    public void destroy() {
        // 清理操作
    }
}
