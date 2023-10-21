package com.example.springboot.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Mapper.UserMapper;
import com.example.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    public boolean saveUser(User user) {
//        if (user.getId()==null){
//            save(user);
//                 } else {
//                updateById(user);
//            }

        return saveOrUpdate(user);
}

//    @Autowired private UserMapper userMapper;
//    /**
//     * 保存用户信息
//     * @param user 用户对象
//     * @return 保存成功的用户ID
//     */
//    public  int save(User user){
//        if (user.getId()==null){
//          return   userMapper.insert(user);
//        }else {
//          return   userMapper.update(user);
//        }
//    };

}
