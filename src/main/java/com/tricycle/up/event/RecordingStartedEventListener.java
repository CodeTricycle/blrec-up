package com.tricycle.up.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.mapper.LiveMapper;
import com.tricycle.up.mapper.RecordeMapper;
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
    private RecordeMapper recordeMapper = Singleton.get(RecordeMapper.class);
    private LiveMapper liveMapper = Singleton.get(LiveMapper.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Recorde recorde = EventUtil.toRecordeEvent(object);
        Live live = liveMapper.getLiveByRoomId(recorde.getRoomId());
        if (Objects.isNull(live)){
            live = EventUtil.toLiveEvent(object);
            liveMapper.insert(live);//初始化直播间
        }

        Recorde lastRecorde = recordeMapper.getLastByRoomId(recorde.getRoomId());
        Date fileCloseTime = null;
        Date fileOpenTime = new Date();
        if (lastRecorde != null
                && (fileCloseTime = lastRecorde.getLiveStopTime()) != null
                && !fileOpenTime.after(DateUtil.offsetMinute(fileCloseTime, 10))) {
            //不是第一次录制并且上次录制完成时间没超过十分钟 继续录制
            lastRecorde.setLiveStopTime(null);
            recordeMapper.updateById(lastRecorde);
            return;
        }

        recorde.setLiveStartTime(new Date());//设置开播时间
        recordeMapper.insert(recorde);
    }
}
