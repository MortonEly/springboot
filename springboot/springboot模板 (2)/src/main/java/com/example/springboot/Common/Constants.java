package com.example.springboot.Common;

public interface Constants {
    /***
     *
     * 表示请求成功，一般用于GET请求和部分POST请求
     */
    String CODE_200="200";//服务器已成功处理了请求
    /***
     * 表示资源创建成功，一般用于POST请求
    **/
    String CODE_201="201";//post成功
    /**
    *表示请求成功，但没有返回内容，一般用于DELETE请求。
    * */
    String CODE_204="204";//删除成功
    String CODE_400="400";//错误请求
    String CODE_500="500";//服务器内部错误
    String CODE_401="401";//未授权
    String CODE_600="600";//其他异常
}
