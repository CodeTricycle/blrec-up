package com.tricycle.up.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Video;
import com.tricycle.up.service.VideoService;
import com.tricycle.up.task.UploadTask;
import com.tricycle.up.util.BiliUtil;
import com.tricycle.up.util.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:21
 * @description
 */
public class VideoPostprocessingCompletedEventListener extends EventListener {
    @Autowired
    private VideoService videoService;

    @Override
    public void execute(JSONObject object) throws Exception {
        Video videoEvent = EventUtil.toVideoEvent(object);
        Video video = videoService.getVideoByPath(videoEvent.getPath());
        if (Objects.isNull(video))
            return;

        BeanUtil.copyProperties(videoEvent, video,"recordeId","id","fileOpenTime");
        System.out.println(video);
        video.setFileCloseTime(new Date());
        videoService.updateById(video);

        BiliUtil.uploadService.submit(new UploadTask(video));
    }
}
