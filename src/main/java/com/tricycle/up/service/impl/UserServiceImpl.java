package com.tricycle.up.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tricycle.up.entity.User;
import com.tricycle.up.mapper.UserMapper;
import com.tricycle.up.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/16 3:19
 * @description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Override
    public User getUserByUserId(String userId) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getDedeUserId, userId);

        return this.getOne(wrapper);
    }
}
