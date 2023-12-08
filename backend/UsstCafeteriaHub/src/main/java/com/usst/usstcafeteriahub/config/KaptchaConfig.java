package com.usst.usstcafeteriahub.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 验证码配置类  登录注册时可能用得到
 * @author 李英杰
 */
@Configuration
public class KaptchaConfig {


    // 这里使用了静态变量，模仿captureConfig 的处理，因为没有这个一直报kaptcha和login的session不同
    public static Map<String, String> CAPTURE_MAP = new HashMap<>();



    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");// 图片边框
        properties.setProperty("kaptcha.border.color", "20,15,90");// 边框颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue"); // 字体颜色
        properties.setProperty("kaptcha.image.width", "110");// 图片宽
        properties.setProperty("kaptcha.image.height", "40");// 图片高
        properties.setProperty("kaptcha.textproducer.font.size", "30");// 字体大小
        properties.setProperty("kaptcha.session.key", "code");// session key
        properties.setProperty("kaptcha.textproducer.char.length", "5");// 验证码长度
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");// 字体
        Config config = new Config(properties);
        captchaProducer.setConfig(config);
        return captchaProducer;

    }
}
