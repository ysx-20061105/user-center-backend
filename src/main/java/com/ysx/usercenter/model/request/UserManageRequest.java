package com.ysx.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserManageRequest implements Serializable {
    private static final long serialVersionUID = -2057219832779046616L;

    private String username;

    private Integer userStatus;
}
