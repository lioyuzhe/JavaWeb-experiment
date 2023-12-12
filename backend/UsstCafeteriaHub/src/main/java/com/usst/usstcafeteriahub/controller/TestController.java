package com.usst.usstcafeteriahub.controller;


import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// 大家编写controller的时候，一定要严格按照这个格式来写，一定要写好注释，虽然比较麻烦，但是现在时团队管理，随意大家一定
// 严格按照要求来。
/**
 * 接口测试和规范
 * @author 李英杰
 */
@Slf4j
@RestController    // 一定要加上  @RestController 是一个 Spring MVC 的注解，用于创建 RESTful web services。它是一个组合注解，等同于 @Controller 和 @ResponseBody 的组合。

@RequestMapping("/test")
public class TestController {
    // 写一个请求

    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public BaseResponse test(){
        return Result.success("功能还未开发");
    }

    @ApiOperation(value = "测试接口2")
    @GetMapping("/test2")
    public BaseResponse test2(@RequestParam String username){
        return Result.error("功能还未开发");
    }




}
