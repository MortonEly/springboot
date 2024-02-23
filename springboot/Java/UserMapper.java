package com.example.springboot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
//@Mapper: 这个注解一般使用在Dao层接口上，相当于一个mapper.xml文件，它的作用就是将接口生成一个动态代理类。加入了@Mapper注解，目的就是为了不再写mapper映射文件。这个注解就是用来映射mapper.xml文件的。
//
//        使用@mapper后，不需要在spring配置中设置扫描地址，通过mapper.xml里面的namespace属性对应相关的mapper类，spring将动态的生成Bean后注入到ServiceImpl中
//
//        注意：
//        在Dao层不要存在相同名字的接口，也就是在Dao不要写重载。因为mapper文件是通过id与接口进行对应的，如果写了两个同名的接口，就会导致mapper文件映射出错。
@Mapper
public interface UserMapper  extends BaseMapper<User> {
//    @Select("select * from user1")
//    List<User> findAll();
//    @Insert("INSERT INTO user1 (username, password, nickname, email, phone, address) VALUES (#{username}, #{password}, #{nickname}, #{email}, #{phone}, #{address})")
//    int insert(User user);
//    @Update("UPDATE user1 SET username = #{username}, password = #{password}, nickname = #{nickname}, email = #{email}, phone = #{phone}, address = #{address} WHERE id = #{id}")
//    int update(User user);
//    @Delete("DELETE FROM user1 WHERE id = #{id}")
//    int deleteById(Integer id);
//    @Select("select * from user1 where username like concat('%', #{username}, '%') limit #{pageNum}, #{pageSize}")
//    List<User> selectPage(@Param("pageNum") Integer startRow, @Param("pageSize") Integer pageSize, @Param("username") String username);
//
//    @Select("select count(*) from user1 where username like concat('%', #{username}, '%')")
//    Integer selectTotal(@Param("username") String username);
//    @Select("select * from user1 where username like #{username} limit #{pageNum}, #{pageSize}")
//    List<User> selectPage(@Param("pageNum") Integer startRow, @Param("pageSize") Integer pageSize, @Param("username") String username);
//    @Select("select count(*) from user1 where username like concat('%', #{username}, '%')")
//    Integer selectTotal(@Param("username") String username);

    /***
     * 单纯的分页接口
     */
//    @Select("select * from user1  limit #{pageNum},#{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize);
//    @Select("select count(*) from user1 ")
//    Integer selectTotal();
}
