package com.tricycle.up.task;

import cn.hutool.core.lang.Singleton;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.User;
import com.tricycle.up.entity.Video;
import com.tricycle.up.framework.Init;
import com.tricycle.up.mapper.LiveMapper;
import com.tricycle.up.mapper.UserMapper;
import com.tricycle.up.mapper.VideoMapper;
import com.tricycle.up.util.BiliUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 22:14
 * @description 上传任务
 */
@Slf4j
public class UploadTask implements Runnable {
    private Video video;
    private Live live;
    private User user;
    private VideoMapper videoMapper;
    private LiveMapper liveMapper;
    private UserMapper userMapper;

    public UploadTask(Video video) {
        this.video = video;
        this.videoMapper = Singleton.get(VideoMapper.class);
        this.liveMapper = Singleton.get(LiveMapper.class);
        this.userMapper = Singleton.get(UserMapper.class);

        this.video = video;
        this.live = this.liveMapper.getLiveByRoomId(video.getRoomId());
        this.user = this.userMapper.getUserByUserId(this.live.getUserId());
    }

    @Override
    public void run() {
        try {
            File file = new File(this.video.getPath());
            if (!file.exists() || Objects.isNull(this.user)) {
                //文件不存在
                this.video.setSuccess(-1);
                videoMapper.updateById(this.video);
                return;
            }
            BiliUtil.preUpload(this.video, this.user);
            BiliUtil.uploadFile(file, this.video);
            if (this.video.getSuccess() == 1) {
                log.info(file.getName() + "上传完成！");
            }
            videoMapper.updateById(this.video);
        } catch (Exception e) {
            e.printStackTrace();
            BiliUtil.uploadService.submit(this);
            return;//终止任务
        }
    }
}
