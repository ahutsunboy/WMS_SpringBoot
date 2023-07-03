package com.ahut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ahut.pojo.User;
import com.ahut.service.UserService;
import com.ahut.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86187
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-06-27 20:32:10
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




