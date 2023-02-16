package com.tricycle.up.task;


import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.User;
import com.tricycle.up.entity.Video;
import com.tricycle.up.service.LiveService;
import com.tricycle.up.service.RecordeService;
import com.tricycle.up.service.UserService;
import com.tricycle.up.service.VideoService;
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
    private VideoService videoService;
    private RecordeService recordeService;
    private LiveService liveService;
    private UserService userService;

    public ReleaseTask(Recorde recorde){
        this.videoService = SpringUtil.getBean(VideoService.class);
        this.recordeService = SpringUtil.getBean(RecordeService.class);
        this.liveService = SpringUtil.getBean(LiveService.class);
        this.userService = SpringUtil.getBean(UserService.class);

        this.recorde = recorde;
        this.live = liveService.getLiveByRoomId(recorde.getRoomId());
        this.user = userService.getUserByUserId(this.live.getUserId());
    }

    @Override
    public void run() {
        try {
            //获取视频信息
            List<Video> videoList = this.videoService.getVideoListByRecordeId(this.recorde.getId());
            //获取发布成功的视频信息
            Long videoSuccessCount = this.videoService.getVideoListByRecordeIdAndSuccess(this.recorde.getId());
            if (Objects.isNull(videoList) || videoList.size() == 0)
                return;
            if (videoList.size() != videoSuccessCount) {
                return;
            }
            log.info("{}全部上传完成，等待投稿", this.recorde.getId());

            //发布
            boolean release = BiliUtil.release(this.recorde.getCover(), this.live, videoList, this.user);
            recorde.setSuccess(release);
            recordeService.updateById(recorde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
