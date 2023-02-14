package com.tricycle.up.controller;

import cn.hutool.core.lang.Singleton;
import cn.hutool.http.server.HttpServerRequest;
import com.tricycle.up.entity.Video;
import com.tricycle.up.framework.Action;
import com.tricycle.up.framework.Result;
import com.tricycle.up.mapper.VideoMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 3:41
 * @description 文件功能
 */
@Slf4j
@Action("/video")
public class VideoController {
    private VideoMapper videoMapper = Singleton.get(VideoMapper.class);

    @Action("/getVideoList")
    public Result getVideoList(HttpServerRequest request) {
        String id = request.getParam("id");
        List<Video> videoList = videoMapper.getVideoListByRecordeId(Integer.valueOf(id));
        return Result.getSucc(videoList);
    }
}
