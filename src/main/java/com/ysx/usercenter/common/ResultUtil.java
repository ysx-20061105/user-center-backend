package com.ysx.usercenter.common;

import java.util.Map;

public class ResultUtil {
    public static <T>Result<T> success(T data){
        return new Result(0,data);
    }

    public static Result error(Map<String,Object> map){
        return new Result(-1,map);
    }

    public static <T>Result<T> error(T data){
        return new Result(-1,data);
    }
}
