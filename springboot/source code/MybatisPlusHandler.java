package com.example.demo.core.Handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Auther: 吕宏博
 * @Date: 2024--10--14--15:37
 * @Description:
 */

@Component
public class MybatisPlusHandler implements MetaObjectHandler {
    private static final Logger log = LoggerFactory.getLogger(MybatisPlusHandler.class);
    //插入时候自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
    }
    //更新时候自动填充
    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
    }
}
