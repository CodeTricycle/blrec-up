package com.tricycle.up.event;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.service.RecordeService;
import com.tricycle.up.util.BeanUtil;
import com.tricycle.up.util.EventUtil;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/13 12:13
 * @description
 */
public class RecordingFinishedEventListener extends EventListener {

    private RecordeService recordeService = SpringUtil.getBean(RecordeService.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Recorde recordeEvent = EventUtil.toRecordeEvent(object);
        EventUtil.removeLock(recordeEvent.getRoomId());
        Recorde recorde = recordeService.getLastRecordeByRoomId(recordeEvent.getRoomId());
        if (Objects.isNull(recorde)) {
            return;
        }
        BeanUtils.copyProperties(recordeEvent, recorde, BeanUtil.getNullPropertyNames(recordeEvent));
        recorde.setLiveStopTime(new Date());
        recordeService.updateById(recorde);
    }
}
