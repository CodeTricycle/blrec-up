package com.tricycle.up.event;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;
import com.tricycle.up.mapper.RecordeMapper;
import com.tricycle.up.mapper.VideoMapper;
import com.tricycle.up.util.EventUtil;

import java.util.Date;
import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:20
 * @description
 */
public class VideoFileCreatedEventListener extends EventListener {
    private RecordeMapper recordeMapper = Singleton.get(RecordeMapper.class);
    private VideoMapper videoMapper = Singleton.get(VideoMapper.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Video video = EventUtil.toVideoEvent(object);

        Video lastVideo = videoMapper.getLastVideoByRoomId(video.getRoomId());
        Recorde recorde = recordeMapper.getLastByRoomId(video.getRoomId());

        if (Objects.nonNull(lastVideo)) {
            video.setPIndex(lastVideo.getPIndex() + 1);
        }

        video.setFileOpenTime(new Date());
        video.setRecordeId(recorde.getId());
        videoMapper.insert(video);
    }
}
