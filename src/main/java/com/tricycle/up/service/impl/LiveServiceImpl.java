package com.tricycle.up.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tricycle.up.entity.Live;
import com.tricycle.up.mapper.LiveMapper;
import com.tricycle.up.service.LiveService;
import org.springframework.stereotype.Service;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/16 3:19
 * @description
 */
@Service
public class LiveServiceImpl extends ServiceImpl<LiveMapper,Live> implements LiveService {
    @Override
    public Live getLiveByRoomId(long roomId) {
        LambdaQueryWrapper<Live> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Live::getRoomId, roomId);
        return this.getOne(wrapper);
    }
}
