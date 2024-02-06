package com.ysx.usercenter.service;

import com.ysx.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.servlet.http.HttpServletRequest;

/**
* @author ysx
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-02-02 14:33:28
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 账户
     * @param userPassword 密码
     * @param checkPassword 密码校验
     * @return 新用户 id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 登陆
     * @param userAccount 账户
     * @param userPassword 密码
     * @param httpServletRequest
     * @return 用户实体
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);

    /**
     * 账户脱敏
     * @param user
     * @return
     */
    User getSafeUser(User user);

    /**
     * 用户登出
     * @param httpServletRequest
     * @return
     */
    int loginOut(HttpServletRequest httpServletRequest);
}
