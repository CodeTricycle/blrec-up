package com.tricycle.up.event;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;
import com.tricycle.up.service.RecordeService;
import com.tricycle.up.service.VideoService;
import com.tricycle.up.util.EventUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:20
 * @description
 */
@Slf4j
public class VideoFileCreatedEventListener extends EventListener {
    private RecordeService recordeService = SpringUtil.getBean(RecordeService.class);
    private VideoService videoService = SpringUtil.getBean(VideoService.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Video video = EventUtil.toVideoEvent(object);
        EventUtil.lock(video.getRoomId());
        log.info("文件新建......");
        Video lastVideo = videoService.getLastVideoByRoomId(video.getRoomId());
        Recorde recorde = recordeService.getLastRecordeByRoomId(video.getRoomId());

        if (Objects.nonNull(lastVideo)) {
            video.setPIndex(lastVideo.getPIndex() + 1);
        }else {
            video.setPIndex(1);
        }
        video.setSuccess(0);
        video.setFileOpenTime(new Date());
        video.setRecordeId(recorde.getId());
        videoService.save(video);
        EventUtil.unlock(video.getRoomId());
    }
}
