package com.usst.usstcafeteriahub;


import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
@RestController
public class UserTest {

    @Test
    @GetMapping("/usertest/getUser")
    public BaseResponse getUser(@RequestParam String username){
        // 1. 判断username是否为空
        if(username == null || username.length() == 0){
            return Result.error("用户名不能为空");
        }

        // 2. 从数据库中取出数据

        // 3. 判断是否存在
            // 3.1 不存在，返回错误信息
            // 3.2 存在，返回用户信息

        // 4. 返回数据
        return Result.success("用户信息");
    }
}
