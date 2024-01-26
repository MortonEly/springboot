package jbdc;

import java.sql.*;

public class JDBCTest1
{
    public static void main(String[] args) throws Exception {
        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //获取连接Connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lv?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai", "root", "dydy521521");
        //得到执行sequel语句的对象Statement
        Statement stmt = conn.createStatement();
        //执行sql语句，并返回结果
        ResultSet rs = stmt.executeQuery("select id,name,password,email,birthday from lv.t_user");

        //处理结果
//        while(rs.next()){
//            System.out.println(rs.getObject("id"));
//            System.out.println(rs.getObject("name"));
//            System.out.println(rs.getObject("password"));
//            System.out.println(rs.getObject("email"));
//            System.out.println(rs.getObject("birthday"));
//            System.out.println("-----------------");
//        }


        //关闭资源
        rs.close();
        stmt.close();
        conn.close();
    }

}