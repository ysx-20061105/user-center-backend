package com.ysx.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求类
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 8250544346040950993L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
