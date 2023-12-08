package com.usst.usstcafeteriahub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
//chenhaoxuan测试
@MapperScan("com.usst.usstcafeteriahub.mapper")
@ServletComponentScan
@SpringBootApplication
public class UsstCafeteriaHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsstCafeteriaHubApplication.class, args);
    }

}
