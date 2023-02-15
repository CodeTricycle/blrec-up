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

    //select * from video where path = ?
    Video getVideoByPath(String path);

    //select * from video where room_id = ? order by id desc limit 1
    Video getLastVideoByRoomId(long roomId);

    List<Video> getNotUploadList();

    Long getNotUploadList(Integer recordeId);

    //select * from video where recorde_id = ?
    List<Video> getVideoListByRecordeId(int id);

    //select * from video where recorde_id = ? and success = true
    List<Video> getVideoListByRecordeIdAndSuccess(int id);
    Video getVideoById(Integer id);
}
