package com.abocode.swagger2doc.asciidoc.web;

import com.abocode.swagger2doc.asciidoc.command.CreateConfigCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 配置信息 前端控制器
 * </p>
 *
 * @author guanxf
 * @since 2018-04-08
 */
@RestController
@RequestMapping("configs")
@Api(value = "配置信息", tags = "配置信息")
public class ConfigController {
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "添加配置信息" , notes = "添加配置信息<br>错误码说明：<br/>BASIC_1000:该城市已经存在<br>")
    public void create(@RequestBody @Valid CreateConfigCommand command) {
      return;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "修改配置信息", notes = "修改配置信息")
    public void update(@PathVariable Integer id,@RequestBody @Valid CreateConfigCommand command) {
        return;
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据配置信息id获取配置信息", notes = "根据配置信息id获取配置信息")
    public CreateConfigCommand get(@PathVariable Integer id)  {
        return new CreateConfigCommand();
    }

    /***
     * 前端
     * @param code
     * @return
     */
    @GetMapping(value = "/code/{code}")
    @ApiOperation(value = "根据code获得配置信息", notes = "根据code获得配置信息")
    public String  getByName(@PathVariable String code) {
      return "";
    }
}
