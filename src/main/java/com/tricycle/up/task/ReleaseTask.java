package com.tricycle.up.task;


import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.StrUtil;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.User;
import com.tricycle.up.entity.Video;
import com.tricycle.up.mapper.LiveMapper;
import com.tricycle.up.mapper.RecordeMapper;
import com.tricycle.up.mapper.UserMapper;
import com.tricycle.up.mapper.VideoMapper;
import com.tricycle.up.util.BiliUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/8 0:50
 * @description 发布任务
 */
@Slf4j
public class ReleaseTask implements Runnable {

    private Live live;
    private User user;
    private Recorde recorde;
    private VideoMapper videoMapper;
    private RecordeMapper recordeMapper;
    private LiveMapper liveMapper;
    private UserMapper userMapper;

    public ReleaseTask(Recorde recorde){
        this.recorde = recorde;
        this.videoMapper = Singleton.get(VideoMapper.class);
        this.recordeMapper = Singleton.get(RecordeMapper.class);
        this.liveMapper = Singleton.get(LiveMapper.class);

        this.live = liveMapper.getLiveByRoomId(recorde.getRoomId());
        this.userMapper = Singleton.get(UserMapper.class);
        this.user = userMapper.getUserByUserId(this.live.getUserId());
    }

    @Override
    public void run() {
        try {
            //获取视频信息
            List<Video> videoList = this.videoMapper.getVideoListByRecordeId(this.recorde.getId());
            //获取发布成功的视频信息
            List<Video> videoSuccessList = this.videoMapper.getVideoListByRecordeIdAndSuccess(this.recorde.getId());
            if (Objects.isNull(videoList) || videoList.size() == 0)
                return;
            if (videoList.size() != videoSuccessList.size()) {
                return;
            }
            log.info("{}全部上传完成，等待投稿", this.recorde.getId());

            //发布
            boolean release = BiliUtil.release(this.recorde.getCover(), this.live, videoList, this.user);
            recorde.setSuccess(release);
            recordeMapper.updateById(recorde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
