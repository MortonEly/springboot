package com.duck;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 代码自动生成配置 3.5.2
public class DuckCode {
    public static void main(String[] args){

        // 要生成的表名
        List<String> list = new ArrayList<>();
        list.add("sys_menu");

        FastAutoGenerator.create("jdbc:mysql://121.5.191.225:3306/ss_test_1?useSSl=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "ss_test_1","WkhSFaawYBcy8GHp")
                .globalConfig(builder -> {
                    builder.author("Duck") // 设置作者
                            .disableOpenDir()//禁止打开输出目录
                            .outputDir(System.getProperty("user.dir")+"/src/main/java") // 指定输出目录
                            .enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd") // 设置注释日期的格式
                            .fileOverride(); // 覆盖已生成文件
                })
                .packageConfig(builder -> {
                    builder.parent("com.duck") // 设置父包名
                            //.moduleName("system") // 设置父包模块名
                            .entity("pojo")
                            .service("service")
                            .serviceImpl("serviceImpl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,System.getProperty("user.dir")+"/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(list) // 设置需要生成的表名
                            .addTablePrefix("sys_") // 设置过滤表前缀
                            .serviceBuilder() // service策略配置
                            .formatServiceFileName("%sService") // 格式化service文件名
                            .formatServiceImplFileName("%sServiceImpl") // 格式化serviceImpl文件名
                            .entityBuilder() // 实体策略配置
                            .enableLombok() // 开启 lombok 模型
                            .logicDeleteColumnName("delete") // 逻辑删除字段名
                            .enableTableFieldAnnotation() // 开启生成实体时生成字段注解
                            .controllerBuilder() // controller 策略配置
                            .formatFileName("%sController") // 格式化文件名称
                            .enableHyphenStyle() // 开启驼峰全名字段
                            .enableRestStyle() // 开启生成@RestController 控制器
                            .mapperBuilder() // mapper 策略配置
                            .superClass(BaseMapper.class) // 设置父类
                            .formatMapperFileName("%sMapper") // 格式化 mapper 文件名称
                            .enableMapperAnnotation() // 开启@Mapper注解
                            .formatXmlFileName("%sMapper"); // 格式化 xml 实现类文件名称

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
