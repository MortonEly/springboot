package com.abocode.swagger2doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author szl
 * @date 2017/1/9
 */
@SpringBootApplication
@EnableSwagger2
public class DocApplication {
    public static void main(String[] args){
        SpringApplication.run(DocApplication.class, args);
    }

}
