package com.tricycle.up.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.User;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/16 3:19
 * @description
 */
public interface UserService extends IService<User> {
    //"select * from user where dede_user_id = ?"
    User getUserByUserId(String userId);
}
