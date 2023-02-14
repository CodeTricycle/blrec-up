package com.tricycle.up.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Live;
import com.tricycle.up.mapper.LiveMapper;
import com.tricycle.up.util.EventUtil;

import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:20
 * @description 开播事件
 */
public class LiveEventListener extends EventListener {
    private LiveMapper liveMapper = Singleton.get(LiveMapper.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Live liveEvent = EventUtil.toLiveEvent(object);

        Live live = liveMapper.getLiveByRoomId(liveEvent.getRoomId());
        if (Objects.isNull(live)) {
            //新建直播间
            live = new Live();
            BeanUtil.copyProperties(liveEvent, live);
            liveMapper.insert(live);
            return;
        }
        //修改直播间信息
        live.setCover(liveEvent.getCover());
        live.setTitle(liveEvent.getTitle());
        liveMapper.updateById(live);
    }
}
