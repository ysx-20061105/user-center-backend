package com.ysx.usercenter.mapper;

import com.ysx.usercenter.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ysx
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2024-02-02 14:33:28
* @Entity com.ysx.usercenter.model.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




