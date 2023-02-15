package com.tricycle.up.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tricycle.up.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 20:56
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
