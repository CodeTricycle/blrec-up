package com.tricycle.up.controller;

import com.tricycle.up.entity.Video;
import com.tricycle.up.framework.Result;
import com.tricycle.up.service.VideoService;
import com.tricycle.up.task.UploadTask;
import com.tricycle.up.util.BiliUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 3:41
 * @description 文件功能
 */
@Slf4j
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @GetMapping("/getVideoList")
    public Result getVideoList(@RequestParam("id") Integer id) {
        List<Video> videoList = videoService.getVideoListByRecordeId(id);
        return Result.getSucc(videoList);
    }

    @PostMapping("/uploadVideo")
    public Result uploadVideo(@RequestParam("id") Integer id) {
        Video video = videoService.getVideoById(id);
        if (video == null) {
            Result.getFail();
        }
        BiliUtil.uploadService.submit(new UploadTask(video));
        return Result.getSucc();
    }
}
