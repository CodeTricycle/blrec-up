package com.tricycle.up.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Live;
import com.tricycle.up.service.LiveService;
import com.tricycle.up.util.EventUtil;

import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:20
 * @description 开播事件
 */
public class LiveEventListener extends EventListener {
    private LiveService liveService = SpringUtil.getBean(LiveService.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Live liveEvent = EventUtil.toLiveEvent(object);

        Live live = liveService.getLiveByRoomId(liveEvent.getRoomId());
        if (Objects.isNull(live)) {
            //新建直播间
            live = new Live();
            BeanUtil.copyProperties(liveEvent, live);
            liveService.save(live);
            return;
        }
        //修改直播间信息
        live.setCover(liveEvent.getCover());
        live.setTitle(liveEvent.getTitle());
        liveService.updateById(live);
    }
}
