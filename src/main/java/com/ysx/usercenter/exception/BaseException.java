package com.ysx.usercenter.exception;

import com.ysx.usercenter.constant.ErrorEnum;
import lombok.Data;

@Data
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = -6944231572976755964L;

    private int code;

    private String description;

    public BaseException(ErrorEnum e) {
        super(e.getMsg());
        this.code=e.getStatus();
        this.description=e.getMsg();
    }
}
