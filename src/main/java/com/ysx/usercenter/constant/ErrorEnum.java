package com.ysx.usercenter.constant;

public enum ErrorEnum {
    NO_LOGIN(40000,"未登录"),
    NO_AUTH(40001,"无权限"),
    PARAMS_NULL(40002,"参数为空"),
    PARAMS_ERROR(40003,"参数错误");

    public int status;

    public String msg;

    ErrorEnum(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    ErrorEnum() {
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
