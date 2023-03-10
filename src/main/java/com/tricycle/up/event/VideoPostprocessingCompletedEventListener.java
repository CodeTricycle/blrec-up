package com.tricycle.up.event;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Video;
import com.tricycle.up.service.VideoService;
import com.tricycle.up.task.UploadTask;
import com.tricycle.up.util.BiliUtil;
import com.tricycle.up.util.EventUtil;

import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:21
 * @description
 */
public class VideoPostprocessingCompletedEventListener extends EventListener {

    private VideoService videoService = SpringUtil.getBean(VideoService.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Video videoEvent = EventUtil.toVideoEvent(object);
        Video video = videoService.getVideoByPath(videoEvent.getPath());
        if (Objects.isNull(video))
            return;

        BiliUtil.uploadService.submit(new UploadTask(video));
    }
}
