package com.tricycle.up.event;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.service.LiveService;
import com.tricycle.up.service.RecordeService;
import com.tricycle.up.util.EventUtil;

import java.util.Date;
import java.util.Objects;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/13 12:11
 * @description
 */
public class RecordingStartedEventListener extends EventListener {
    private RecordeService recordeService = SpringUtil.getBean(RecordeService.class);
    private LiveService liveService = SpringUtil.getBean(LiveService.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Recorde recorde = EventUtil.toRecordeEvent(object);
        Live live = liveService.getLiveByRoomId(recorde.getRoomId());
        if (Objects.isNull(live)){
            live = EventUtil.toLiveEvent(object);
            liveService.save(live);//初始化直播间
        }

        Recorde lastRecorde = recordeService.getLastByRoomId(recorde.getRoomId());
        Date fileCloseTime = null;
        Date fileOpenTime = new Date();
        if (lastRecorde != null
                && (fileCloseTime = lastRecorde.getLiveStopTime()) != null
                && !fileOpenTime.after(DateUtil.offsetMinute(fileCloseTime, 10))) {
            //不是第一次录制并且上次录制完成时间没超过十分钟 继续录制
            lastRecorde.setLiveStopTime(null);
            recordeService.updateById(lastRecorde);
            return;
        }

        recorde.setLiveStartTime(new Date());//设置开播时间
        recordeService.save(recorde);
    }
}
