package com.softeem.web.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装服务端向客户端返回的数据
 */
@Data  // 自动添加 get和set，toString方法
@NoArgsConstructor   // 自动添加无参构造器
@AllArgsConstructor  // 自动添加有参构造器
public class Result {

    private int code;  // 状态码  ，  成功 1   失败-1
    private String msg; // 消息
    private Object data;// 返回的数据

    // 成功的状态码
    public static int SUCCESS_CODE = 1;
    // 失败的状态码
    public static int FAILED_CODE = -1;

    /**
     * 操作成功，只需要返回msg时调用此方法
     * @param msg
     * @return
     */
    public static Result succuess(String msg){
        return new Result(SUCCESS_CODE,msg,null);
    }

    /**
     * 操作成功，同时返回msg和data数据
     * @param msg
     * @param data
     * @return
     */
    public static Result succuess(String msg,Object data){
        return new Result(SUCCESS_CODE,msg,data);
    }

    /**
     * 操作成功，仅返回data数据
     * @param data
     * @return
     */
    public static Result succuess(Object data){
        return new Result(SUCCESS_CODE,"",data);
    }


    /**
     * 操作失败
     * @param msg
     * @return
     */
    public static Result failed(String msg){
        return new Result(FAILED_CODE,msg,null);
    }


}
