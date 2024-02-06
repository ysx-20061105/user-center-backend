package com.ysx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ysx.usercenter.constant.ErrorEnum;
import com.ysx.usercenter.exception.BaseException;
import com.ysx.usercenter.mapper.UserMapper;
import com.ysx.usercenter.model.domain.User;
import com.ysx.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.ysx.usercenter.constant.ErrorEnum.PARAMS_ERROR;
import static com.ysx.usercenter.constant.ErrorEnum.PARAMS_NULL;
import static com.ysx.usercenter.constant.UserConstant.USER_LOGIN_SESSION;

/**
* @author ysx
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-02-02 14:33:28
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.账户，密码，密码校验不能为空
        if(StringUtils.isAllBlank(userAccount,userPassword,checkPassword)){
            throw new BaseException(PARAMS_NULL);
        }
        //账户不小于四位
        if(userAccount.length()<4){
            throw new BaseException(PARAMS_ERROR);
        }
        //密码不小于6位
        if(userPassword.length()<6||checkPassword.length()<6){
            throw new BaseException(PARAMS_ERROR);
        }
        //账户不包含特殊字符
        String validPattern = "[a-zA-Z0-9]+";
        boolean matches = userAccount.matches(validPattern);
        if(!matches){
            throw new BaseException(PARAMS_ERROR);
        }
        //密码和校验密码相同
        if(!userPassword.equals(checkPassword)){
            throw new BaseException(PARAMS_ERROR);
        }
        //账户不能相同
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = this.count(queryWrapper);
        if(count>0){
            throw new BaseException(PARAMS_ERROR);
        }
        //2. 密码加密
        String password = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        //3. 保存
        User user=new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(password);
        boolean result = this.save(user);
        if(!result){
            throw new BaseException(PARAMS_ERROR);
        }
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest) {
        //1. 账户、密码不能为空
        if(StringUtils.isAllBlank(userAccount,userPassword)){
            throw new BaseException(PARAMS_NULL);
        }
        //账户不小于4位
        if(userAccount.length()<4){
            throw new BaseException(PARAMS_ERROR);
        }
        //密码不小于6位
        if(userPassword.length()<6){
            throw new BaseException(PARAMS_ERROR);
        }
        //账户不包含特殊字符
        String validPattern = "[a-zA-Z0-9]+";
        boolean matches = userAccount.matches(validPattern);
        if(!matches){
            throw new BaseException(PARAMS_ERROR);
        }
        //2. 查询账户
        String password=DigestUtils.md5DigestAsHex(userPassword.getBytes());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount)
                .eq("userPassword",password);
        User user = baseMapper.selectOne(queryWrapper);
        //账户是否存在
        if(user==null){
            throw new BaseException(PARAMS_ERROR);
        }
        User cleanUser = getSafeUser(user);
        //4. 保存session
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(USER_LOGIN_SESSION,cleanUser);
        //5. 返回脱敏数据
        return cleanUser;
    }

    /**
     * 账户脱敏
     * @param user
     * @return
     */
    @Override
    public User getSafeUser(User user){
        //3. 账户脱敏
        User cleanUser=new User();
        cleanUser.setId(user.getId());
        cleanUser.setUsername(user.getUsername());
        cleanUser.setUserAccount(user.getUserAccount());
        cleanUser.setAvatarUrl(user.getAvatarUrl());
        cleanUser.setGender(user.getGender());
        cleanUser.setPhone(user.getPhone());
        cleanUser.setEmail(user.getEmail());
        cleanUser.setUserRole(user.getUserRole());
        cleanUser.setUserStatus(user.getUserStatus());
        cleanUser.setCreateTime(user.getCreateTime());
        return cleanUser;
    }

    @Override
    public int loginOut(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute(USER_LOGIN_SESSION);
        return 1;
    }
}




