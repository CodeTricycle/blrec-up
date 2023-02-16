package com.tricycle.up.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;

import java.util.List;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/16 3:19
 * @description
 */
public interface VideoService extends IService<Video> {

    Video getVideoByPath(String path);

    Video getLastVideoByRoomId(long roomId);

    List<Video> getNotUploadList();

    Long getNotUploadList(Integer recordeId);

    List<Video> getVideoListByRecordeId(int id);

    Long getVideoListByRecordeIdAndSuccess(int id);

    Video getVideoById(Integer id);
}
