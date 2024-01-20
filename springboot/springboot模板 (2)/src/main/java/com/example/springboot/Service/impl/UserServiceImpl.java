package com.example.springboot.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.Common.Constants;
import com.example.springboot.Common.ResponseEnum;
import com.example.springboot.Common.Result;
import com.example.springboot.Controller.Login.UserControllerLogin;
import com.example.springboot.Exception.ServiceException;
import com.example.springboot.entity.User;
import com.example.springboot.Mapper.UserMapper;
import com.example.springboot.Service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lv
 * @since 2023-10-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG = Log.get();
    @Autowired
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result login(UserControllerLogin userControllerLogin) {
        String username =userControllerLogin.getUsername();
        String password =userControllerLogin.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User  userList = userMapper.selectOne(queryWrapper);
        if (userList !=null){
            //登录成功
            String token= TokenUtil.getToken(userList.getId().toString(),password);
            String  userJson = JSON.toJSONString(userList);
            stringRedisTemplate.opsForValue().set(token,userJson,1, TimeUnit.DAYS);
            userControllerLogin.setToken(token);
            userControllerLogin.setNickname(userList.getNickname());
            userControllerLogin.setHeaderUrl(userList.getHeaderUrl());
            //

            return Result.success(userControllerLogin);

        }else {
            return Result.error("400","用户名或密码出现问题");
        }

    }

    @Override
    public Result register(UserControllerLogin userControllerLogin) {
        String username=userControllerLogin.getUsername();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> userList = userMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(userList)){
            return Result.error("500","用户名已存在");

        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(userControllerLogin.getPassword());
        userMapper.insert(user);
        return Result.success("注册成功，将跳转到登录页面");
    }
/***    第一种登录和注册逻辑代码
 * 2023年完成
 */

    /***用户登录逻辑
     *
     * @param userControllerLogin
     * @return
     */
//    @Override
//    public UserControllerLogin login(UserControllerLogin userControllerLogin) {
//        User one = getUserInfo(userControllerLogin);
//        if (one != null) {
//            BeanUtil.copyProperties(one, userControllerLogin, true);
//          String token= TokenUtil.getToken(one.getId().toString(),one.getPassword());
//            userControllerLogin.setToken(token);
//            return userControllerLogin;
//        } else {
//            throw new ServiceException(Constants.CODE_600, "用户或密码错误");
//        }
//    }

    /***用户注册逻辑
     *
     * @param userControllerLogin
     * @return
     */
//    @Override
//    public UserControllerLogin register(UserControllerLogin userControllerLogin) {
//        User existingUser = getUserInfo(userControllerLogin);
//        if (existingUser != null) {
//            throw new ServiceException(Constants.CODE_600, "用户已存在");
//        } else {
//            User newUser = new User();
//            BeanUtil.copyProperties(userControllerLogin, newUser, true);
//            save(newUser);
//            return userControllerLogin;
//        }
//    }

    /*** 根据登录信息获取用户信息
     *
     * @param userControllerLogin
     * @return
     */
//    private User getUserInfo(UserControllerLogin userControllerLogin) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", userControllerLogin.getUsername());
//        queryWrapper.eq("password", userControllerLogin.getPassword());
//        User user;
//        try {
//            user = getOne(queryWrapper);
//        } catch (Exception e) {
//            LOG.error(e);
//            throw new ServiceException(Constants.CODE_500, "系统错误");
//        }
//        return user;
//    }
}

