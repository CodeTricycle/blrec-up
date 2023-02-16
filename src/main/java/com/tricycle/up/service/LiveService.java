package com.tricycle.up.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/16 3:19
 * @description
 */
public interface LiveService extends IService<Live> {
    Live getLiveByRoomId(long roomId);
}
