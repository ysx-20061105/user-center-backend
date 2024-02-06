package com.ysx.usercenter.handler;

import com.ysx.usercenter.common.Result;
import com.ysx.usercenter.common.ResultUtil;
import com.ysx.usercenter.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result customizeExceptionHandler(BaseException e){
        log.error("自定义异常",e);
        Map<String,Object> map=new HashMap<>();
        map.put("errorCode",e.getCode());
        map.put("description",e.getDescription());
        return ResultUtil.error(map);
    }
}
