package com.tricycle.up.mapper;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 20:56
 * @description
 */
public class VideoMapper {
    public void insert(Video video) {
        try {
            Long key = Db.use().insertForGeneratedKey(
                    Entity.create("video").set("recorde_id", video.getRecordeId())
                            .set("create_time", new Date()).set("room_id", video.getRoomId())
                            .set("path", video.getPath()).set("p_index", video.getPIndex())
                            .set("file_open_time", video.getFileOpenTime()).set("file_close_time", video.getFileCloseTime())
                            .set("url", video.getUrl())
                            .set("filename", video.getFilename()).set("complete", video.getComplete())
                            .set("success", video.getSuccess())
            );
            video.setId(key.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateById(Video video) {
        try {
            Db.use().update(
                    Entity.create().set("recorde_id", video.getRecordeId())
                            .set("update_time", new Date()).set("room_id", video.getRoomId())
                            .set("path", video.getPath()).set("p_index", video.getPIndex())
                            .set("file_open_time", video.getFileOpenTime()).set("file_close_time", video.getFileCloseTime())
                            .set("url", video.getUrl())
                            .set("filename", video.getFilename()).set("complete", video.getComplete())
                            .set("success", video.getSuccess()),
                    Entity.create("video").set("id", video.getId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Video getVideoByPath(String path) {
        try {
            return Db.use().query("select * from video where path = ?", Video.class, path).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public Video getLastVideoByRoomId(long roomId) {
        try {
            return Db.use().query("select * from video where room_id = ? order by id desc limit 1", Video.class, roomId).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Video> getNotUploadList() {
        try {
            List<Video> uploadNotList = Db.use().query("select * from video where success = 0", Video.class);//未发布的文件

            DateTime currentDate = DateUtil.date();//当前时间
            List<Video> videoList = uploadNotList.stream().filter(video -> {
                if (video.getFileCloseTime() != null) {
                    if (currentDate.after(video.getFileCloseTime())) {
                        //视频文件已结束
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            return videoList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Video> getVideoListByRecordeId(int id) {
        try {
            return Db.use().query("select * from video where recorde_id = ?", Video.class, id);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Video> getVideoListByRecordeIdAndSuccess(int id) {
        try {
            return Db.use().query("select * from video where recorde_id = ? and success = true", Video.class, id);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
