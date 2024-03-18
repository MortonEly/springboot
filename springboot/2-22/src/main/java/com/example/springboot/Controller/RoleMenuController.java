package com.example.springboot.Controller;

import com.example.springboot.Common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 吕宏博
 * @Date: 2024/02/15/21:25
 * @Description: sys_role_menu方法类
 */
@Api(value = "test接口方法", tags = "test管理相关的接口")
@RestController
@RequestMapping("/test")
public class RoleMenuController {
    @ApiOperation(value = "查询User所有数据")
    @GetMapping("/findAll")
    public Result ok(){
        return Result.success(200,"查询成功");
    }
}
