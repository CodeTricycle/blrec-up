package com.tricycle.up.framework;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;
import com.tricycle.up.mapper.RecordeMapper;
import com.tricycle.up.mapper.VideoMapper;
import com.tricycle.up.task.ReleaseTask;
import com.tricycle.up.task.UploadTask;
import com.tricycle.up.util.BiliUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/14 2:36
 * @description 添加定时任务
 */
@Slf4j
public class ScheduleInit extends Init {

    private static RecordeMapper recordeMapper = Singleton.get(RecordeMapper.class);

    @Override
    public void start() {
        //upload();

        CronUtil.schedule("0 0/15 * * * ?", (Task) () -> release());
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }


    public void upload() {
        log.info("触发初始化任务-上传文件");
        VideoMapper videoMapper = Singleton.get(VideoMapper.class);
        List<Video> videoList = videoMapper.getNotUploadList();
        videoList.forEach(video -> {
            BiliUtil.uploadService.submit(new UploadTask(video));
        });
    }

    public void release() {
        log.info("触发定时任务-发布投稿");
        List<Recorde> releaseList = recordeMapper.getNotReleaseList();
        releaseList.forEach(recorde -> {
            new ReleaseTask(recorde).run();
            ThreadUtil.sleep(35 * 1000);//间隔35秒
        });
    }
}
