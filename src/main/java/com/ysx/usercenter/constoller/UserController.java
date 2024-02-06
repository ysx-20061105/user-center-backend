package com.ysx.usercenter.constoller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ysx.usercenter.common.Result;
import com.ysx.usercenter.common.ResultUtil;
import com.ysx.usercenter.exception.BaseException;
import com.ysx.usercenter.model.domain.User;
import com.ysx.usercenter.model.request.UserLoginRequest;
import com.ysx.usercenter.model.request.UserRegisterRequest;
import com.ysx.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ysx.usercenter.constant.ErrorEnum.*;
import static com.ysx.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.ysx.usercenter.constant.UserConstant.USER_LOGIN_SESSION;

/**
 * 用户接口控制
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public Result<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest==null)
            throw new BaseException(PARAMS_NULL);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAllBlank(userAccount, userPassword, checkPassword))
            throw new BaseException(PARAMS_NULL);
        Long id=userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtil.success(id);
    }

    /**
     * 用户登陆
     * @param userLoginRequest
     * @return
     */
    @PostMapping("/login")
    public Result<User> userRegister(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest){
        if(userLoginRequest==null)
            throw new BaseException(PARAMS_NULL);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAllBlank(userAccount, userPassword))
            throw new BaseException(PARAMS_NULL);
        User user=userService.doLogin(userAccount, userPassword, httpServletRequest);
        return ResultUtil.success(user);
    }

    @PostMapping("/loginOut")
    public Result<Integer> loginOut(HttpServletRequest httpServletRequest){
        if(httpServletRequest==null){
            throw new BaseException(PARAMS_NULL);
        }
        int res = userService.loginOut(httpServletRequest);
        return ResultUtil.success(res);
    }

    @GetMapping("/current")
    public Result<User> getCurrent(HttpServletRequest httpServletRequest){
        User user = (User)httpServletRequest.getSession().getAttribute(USER_LOGIN_SESSION);
        if(user==null){
            throw new BaseException(PARAMS_NULL);
        }
        Long currentId = user.getId();
        //TODO 校验用户是否合法
        user=userService.getById(currentId);
        httpServletRequest.getSession().removeAttribute(USER_LOGIN_SESSION);
        httpServletRequest.getSession().setAttribute(USER_LOGIN_SESSION,user);
        User safeUser = userService.getSafeUser(user);
        return ResultUtil.success(safeUser);
    }

    @GetMapping("/search")
    public Result<List<User>> searchUsers(String username,HttpServletRequest httpServletRequest){
        if(!isAdmin(httpServletRequest))
            throw new BaseException(NO_AUTH);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNoneBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> list = userService.list(queryWrapper);
        list = list.stream().map(user -> {
            user.setUserPassword(null);
            return user;
        }).collect(Collectors.toList());
        return ResultUtil.success(list);
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody Long id,HttpServletRequest httpServletRequest){
        if(!isAdmin(httpServletRequest))
            return false;
        if(id<=0) return false;
        return userService.removeById(id);
    }

    private boolean isAdmin(HttpServletRequest httpServletRequest){
        User user = (User) httpServletRequest.getSession().getAttribute(USER_LOGIN_SESSION);
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
