package com.ysx.usercenter.service;

import com.ysx.usercenter.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void TestAddUser(){
        User user=new User();
        user.setUsername("ysx");
        user.setUserAccount("123456");
        user.setAvatarUrl("");
        user.setUserPassword("123456");
        user.setGender(0);
        user.setPhone("123");
        user.setEmail("123");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assert.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount="admin";
        String userPassword="1234567";
        String checkPassword="1234567";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result>0);
//        Assertions.assertEquals(-1,result);
//        result = userService.userRegister("123", userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
//        result = userService.userRegister(userAccount, "12345", checkPassword);
//        Assertions.assertEquals(-1,result);
//        result = userService.userRegister("12_ ", userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
//        result = userService.userRegister(userAccount, userPassword, "123456");
//        Assertions.assertEquals(-1,result);
//        result = userService.userRegister("123456", userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertTrue(result>0);
    }

}