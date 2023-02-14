package com.tricycle.up.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.json.JSONObject;
import com.tricycle.up.entity.Video;
import com.tricycle.up.mapper.VideoMapper;
import com.tricycle.up.task.UploadTask;
import com.tricycle.up.util.BiliUtil;
import com.tricycle.up.util.EventUtil;

import java.util.Date;
import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:21
 * @description
 */
public class VideoPostprocessingCompletedEventListener extends EventListener {
    private VideoMapper videoMapper = Singleton.get(VideoMapper.class);

    @Override
    public void execute(JSONObject object) throws Exception {
        Video videoEvent = EventUtil.toVideoEvent(object);
        Video video = videoMapper.getVideoByPath(videoEvent.getPath());
        if (Objects.isNull(video))
            return;

        BeanUtil.copyProperties(videoEvent, video,"fileOpenTime");
        video.setFileCloseTime(new Date());
        videoMapper.updateById(video);

        BiliUtil.uploadService.submit(new UploadTask(video));
    }
}
