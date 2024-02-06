package com.ysx.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登陆请求类
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 863932331841910764L;

    private String userAccount;

    private String userPassword;
}
