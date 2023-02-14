package com.tricycle.up.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.mapper.RecordeMapper;
import com.tricycle.up.util.EventUtil;

import java.util.Date;
import java.util.Objects;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/13 12:13
 * @description
 */
public class RecordingFinishedEventListener extends EventListener {
    private RecordeMapper recordeMapper = Singleton.get(RecordeMapper.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Recorde recordeEvent = EventUtil.toRecordeEvent(object);
        Recorde recorde = recordeMapper.getLastByRoomId(recordeEvent.getRoomId());
        if (Objects.isNull(recorde)) {
            return;
        }
        BeanUtil.copyProperties(recordeEvent, recorde, "id", "liveStartTime");
        recorde.setLiveStopTime(new Date());
        recordeMapper.updateById(recorde);
    }
}
