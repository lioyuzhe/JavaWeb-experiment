package com.usst.usstcafeteriahub.filter.wrapper;

import com.usst.usstcafeteriahub.utils.SensitiveFilter;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class SensitiveRequestWrapper extends HttpServletRequestWrapper {

    private final String filteredContent;

    public SensitiveRequestWrapper(HttpServletRequest request, SensitiveFilter sensitiveFilter) throws IOException {
        super(request);

        // 把请求体转换成字符串
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = request.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        }

        // 使用敏感词过滤器处理请求体内容
        this.filteredContent = sensitiveFilter.filter(content.toString());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(filteredContent.getBytes(StandardCharsets.UTF_8));

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}