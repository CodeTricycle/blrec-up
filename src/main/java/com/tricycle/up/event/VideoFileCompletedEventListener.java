package com.tricycle.up.event;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Video;
import com.tricycle.up.service.VideoService;
import com.tricycle.up.util.BeanUtil;
import com.tricycle.up.util.EventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:20
 * @description
 */
public class VideoFileCompletedEventListener extends EventListener {
    private VideoService videoService = SpringUtil.getBean(VideoService.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Video videoEvent = EventUtil.toVideoEvent(object);
        //EventUtil.lock(videoEvent.getRoomId());
        Video video = videoService.getVideoByPath(videoEvent.getPath());
        if (Objects.isNull(video))
            return;

        BeanUtils.copyProperties(videoEvent, video, BeanUtil.getNullPropertyNames(videoEvent));
        video.setFileCloseTime(DateUtil.date());
        videoService.updateById(video);
        //EventUtil.unlock(videoEvent.getRoomId());
    }
}
