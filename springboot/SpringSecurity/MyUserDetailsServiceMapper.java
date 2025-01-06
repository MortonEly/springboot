package Com.Demo.Core.SpareResources;



import java.util.List;

/**
 * @Auther: 吕宏博
 * @Date: 2024--10--29--16:44
 * @Description:
 */
//@Mapper
//public interface MyUserDetailsServiceMapper {
//    //根据userID查询用户信息
//    @Select("SELECT username,password,enabled\n" +
//            "FROM sys_user u\n" +
//            "WHERE u.username = #{userId}")
//    MyUserDetails findByUserName(@Param("userId") String userId);
//
//    //根据userID查询用户角色
//    @Select("SELECT role_code\n" +
//            "FROM sys_role r\n" +
//            "LEFT JOIN sys_user_role ur ON r.id = ur.role_id\n" +
//            "LEFT JOIN sys_user u ON u.id = ur.user_id\n" +
//            "WHERE u.username = #{userId}")
//    List<String> findRoleByUserName(@Param("userId") String userId);


//    //根据用户角色查询用户权限
//    @Select({
//            "<script>",
//            "SELECT url " ,
//            "FROM sys_menu m " ,
//            "LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id " ,
//            "LEFT JOIN sys_role r ON r.id = rm.role_id ",
//            "WHERE r.role_code IN ",
//            "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'>",
//            "#{roleCode}",
//            "</foreach>",
//            "</script>"
//    })
//    List<String> findAuthorityByRoleCodes(@Param("roleCodes") List<String> roleCodes);
//}
