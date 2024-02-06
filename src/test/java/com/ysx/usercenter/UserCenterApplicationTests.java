package com.ysx.usercenter;

import com.ysx.usercenter.model.request.UserManageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void contextLoads() {
        String str = DigestUtils.md5DigestAsHex("123456".getBytes());
        UserManageRequest userManageRequest = new UserManageRequest();
        System.out.println(userManageRequest.getUserStatus());
        System.out.println(str);
    }

}
