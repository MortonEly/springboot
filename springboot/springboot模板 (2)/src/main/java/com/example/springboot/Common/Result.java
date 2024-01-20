package com.example.springboot.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String code;
    private String msg;
    private  Object data;

    public static Result success(){
        return  new Result(Constants.CODE_200,"成功",null);
    }
    public static Result success(Object data){

        return  new Result(Constants.CODE_200,"成功",data);
    }
    public  static Result deleteSuccess(){
        return  new Result(Constants.CODE_204,"成功",null);
    }

    public static  Result deleteSuccess(Object data){
        return  new Result(Constants.CODE_204,"删除成功",data);
    }
    public static Result error(){
        return  new Result(Constants.CODE_500,"系统错误",null);
    }
    public static Result error(String code,String msg){
        return  new Result(code,msg,null);
    }

}
