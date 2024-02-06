package com.ysx.usercenter.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private T data;
    private String msg;

    public Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result(int code ,T data){
        this(code,data,"");
    }

}
