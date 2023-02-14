package com.tricycle.up.mapper;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.tricycle.up.entity.Live;

import java.util.Date;
import java.util.List;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 17:53
 * @description
 */
public class LiveMapper {
    public Live getLiveByRoomId(long roomId) {
        try {
            return Db.use().query("select * from live where room_id = ?", Live.class, roomId).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public void insert(Live live) {
        try {
            Long key = Db.use().insertForGeneratedKey(
                    Entity.create("live")
                            .set("create_time", new Date()).set("name", live.getName())
                            .set("room_id", live.getRoomId()).set("title", live.getTitle())
                            .set("cover", live.getCover()).set("tags", live.getTags())
                            .set("title_template", live.getTitleTemplate())
                            .set("copyright", live.isCopyright()).set("desc", live.getDesc())
                            .set("part_title_template", live.getPartTitleTemplate()).set("upload", live.isUpload())
                            .set("user_id", live.getUserId()).set("tid", live.getTid()));
            live.setId(key.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateById(Live live) {
        try {
            Db.use().update(
                    Entity.create()
                            .set("update_time", new Date()).set("name", live.getName())
                            .set("room_id", live.getRoomId()).set("title", live.getTitle())
                            .set("cover", live.getCover()).set("tags", live.getTags())
                            .set("title_template", live.getTitleTemplate())
                            .set("copyright", live.isCopyright()).set("desc", live.getDesc())
                            .set("part_title_template", live.getPartTitleTemplate()).set("upload", live.isUpload())
                            .set("user_id", live.getUserId()).set("tid", live.getTid()),
                    Entity.create("live").set("id", live.getId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Live> getLiveList() {
        try {
            return Db.use().query("select * from live", Live.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteById(int id) {
        try {
            Db.use().del("live", "id", id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

