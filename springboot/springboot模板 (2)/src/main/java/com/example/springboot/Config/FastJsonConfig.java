//package com.example.springboot.Config;
//
//import com.alibaba.fastjson.serializer.SerializeConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Configuration
//public class FastJsonConfig {
//
//    @Bean
//    public com.alibaba.fastjson.support.config.FastJsonConfig fastJsonConfig() {
//        com.alibaba.fastjson.support.config.FastJsonConfig config = new com.alibaba.fastjson.support.config.FastJsonConfig();
//
//        // 创建 SerializeConfig，并设置日期格式
//        SerializeConfig serializeConfig = new SerializeConfig();
//        serializeConfig.put(Date.class, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        config.setSerializeConfig(serializeConfig);
//
//        return config;
//    }
//}
