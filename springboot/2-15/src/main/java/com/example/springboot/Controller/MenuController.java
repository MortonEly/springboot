package com.example.springboot.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.Common.Result;
import com.example.springboot.Service.IMenuService;
import com.example.springboot.entity.Menu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @Author lv
 * @Date 2024-01-17
 */
@Api(value = "menu接口", tags = "菜单管理相关的接口")
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    /**
     * 新增/修改
     * menu菜单信息
     * */
    @ApiOperation(value = "根据id修改/保存菜单方法接口")
    @PostMapping("/save")
    public Result save(@RequestBody Menu menu) {
        boolean  b= menuService.saveOrUpdate(menu);
        if (b){
            return Result.success(200,"菜单保存成功",menu); // 使用无参的 success() 方法
        }else
        {
            return  Result.error();
        }
    }
   /**
    * 查询全部菜单
    *构造树形菜单接口
    * */
   @ApiOperation(value = "根据name修改积分等级")
    @PostMapping("/findAll")
    public Result findAll(@RequestParam(name = "name",defaultValue = "") String name){
        List<Menu> list = menuService.findAll(name);
        return Result.success(200,"查询菜单成功",list);
    }

    @ApiOperation(value = "树形菜单栏")
    @PostMapping("/findAllMenu")
    public Result findAllMenu(@RequestParam(name = "name",defaultValue = "") String name){
        List<Menu> list = menuService.list();
        List<Integer> menuIdList = list.stream().map(menu -> menu.getId()).collect(Collectors.toList());
        return Result.success(menuIdList);
    }

    /***删除菜单接口，根据id进行删除单个
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除菜单")
    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean delete = menuService.removeById(id);
        if(delete)
        {
            return Result.success();
        } else
        {
            return Result.error();
        }
    }
    /***
     *id批量删除角色
     * **/
    @ApiOperation(value = "根据id批量删除菜单")
    @DeleteMapping("/deleteBatch/{id}")
    public Result deleteBatch(@RequestBody List<Integer> idList) {
        boolean delete = menuService.removeByIds(idList);
        if(delete)
        {
            return Result.success();
        } else
        {
            return Result.error();
        }
    }
    /****分页接口
     *
     */
    @ApiOperation(value = "根据菜单页码和页数分页")
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name
    ) {
        Page<Menu> page = new Page(pageNum,pageSize);
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        IPage<Menu> menuPage = menuService.page(page, queryWrapper);
        return Result.success(menuPage);
    }
}
