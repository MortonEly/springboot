package com.example.springboot.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.Mapper.UserMapper;
import com.example.springboot.Service.UserService;
import com.example.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
//@Controller 的衍生注解
public class UserController {
//    @Autowired
//    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @GetMapping("/index")
    public List<User> index(){

        return userService.list();
    }
    //新增和修改
    @PostMapping("/insert")
    public boolean save(@RequestBody User user){
        return  userService.saveUser(user);
    }
    /**
     使用HTTP DELETE方法映射到"/user/delete/{id}"路由,接收一个PathVariable参数id，
     并通过调用userMapper的deleteById(id)方法删除指定id的数据。
     * */
//    @DeleteMapping("/delete/{id}")
//    public  Integer delete(@PathVariable Integer id) {
//        return userMapper.deleteById(id);
//    }
    @DeleteMapping("/{id}")
    public  boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }
    /**
     * 分页查询，mybatis-plus方法
     * */

    @PostMapping ("Delete/Deletebatch/{id}")
    public  boolean DeleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }

    @PostMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String address,
                                @RequestParam(defaultValue = "") String email
//                              @RequestParam(defaultValue = "") String nickname,
//                              @RequestParam(defaultValue = "") String phone



    )
    {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        /**
         * and查询
         * */
        if(!"".equals(username)){
            queryWrapper.like("username", username);
        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }
        if(!"".equals(address)){
            queryWrapper.like("address", address);
        }
queryWrapper.orderByAsc("id");
        return userService.page(page, queryWrapper);
    /***
    * 利用mybatis实现
    */
//    @GetMapping("/page")
//
//    public Map<String, Object> findPage(@RequestParam Integer pageNum,
//                                        @RequestParam Integer pageSize,
//                                        @RequestParam String username) {
//        pageNum = (pageNum - 1) * pageSize;
//        username ="%"+username+"%";
//        List<User> data = userMapper.selectPage(pageNum, pageSize, username);
//        Integer total = userMapper.selectTotal(username);
//        Map<String, Object> res = new HashMap<>();
//        res.put("data", data);
//        res.put("total", total);
//        return res;
//    }
    /***
     * 单纯的分页
     */
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize)
//    {
//        pageNum = (pageNum - 1) * pageSize;
//        List<User> data = userMapper.selectPage(pageNum, pageSize);
//        Integer total = userMapper.selectTotal();
//        Map<String,Object> res= new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);
//        return  res;
//    }

    //    queryWrapper.like("username", username);
//    queryWrapper.like("nickname",nickname);
//    queryWrapper.like("address",address);
//    queryWrapper.like("email",email);
//    queryWrapper.like("phone",phone);
}

}
