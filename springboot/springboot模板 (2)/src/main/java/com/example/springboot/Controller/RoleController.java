package com.example.springboot.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.Common.Result;
import com.example.springboot.Service.IRoleService;
import com.example.springboot.Service.IUserService;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    /**
     * 新增/修改
     * role角色信息
     * */
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
       boolean  b= roleService.saveOrUpdate(role);
       if (b){
           return Result.success(); // 使用无参的 success() 方法
       }else
       {
           return  Result.error();
       }
    }

    /**查询接口
     * 查询菜单所有
     * @return
     */
    @PostMapping("/findAll")
    public Result findAll(){
        return Result.success(roleService.list());
    }

    /***删除接口，根据id进行删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        List<User> list=userService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)){
            return Result.error("500","角色信息已经被使用，无法删除");
        }

        boolean delete = roleService.removeById(id);
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
    @DeleteMapping("/deleteBatch/{id}")
    public Result deleteBatch(@RequestBody List<Integer> idList) {
        for (Integer id : idList) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            List<User> list=userService.list(queryWrapper);
            if (CollectionUtils.isEmpty(list)){
                return Result.error("500","菜单信息已经被使用，无法删除");
            }
        }
        boolean delete = roleService.removeByIds(idList);
        if(delete)
        {
            return Result.success();
        } else
        {
            return Result.error();
        }
    }

    /**分页接口
     * 菜单数据分页接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name
                       ) {
        Page<Role> page = new Page(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        IPage<Role> rolePage = roleService.page(page, queryWrapper);
        return Result.success(rolePage);
    }
}
