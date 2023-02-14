package com.tricycle.up.mapper;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.tricycle.up.entity.Recorde;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 17:53
 * @description
 */
public class RecordeMapper {
    public Recorde getLastByRoomId(long roomId) {
        try {
            return Db.use().query("select * from recorde where room_id = ? order by id desc limit 1", Recorde.class, roomId).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public void insert(Recorde recorde) {
        try {
            Long key = Db.use().insertForGeneratedKey(
                    Entity.create("recorde")
                            .set("create_time", new Date())
                            .set("room_id", recorde.getRoomId())
                            .set("live_start_time", recorde.getLiveStartTime())
                            .set("live_stop_time", recorde.getLiveStopTime()).set("title", recorde.getTitle())
                            .set("cover", recorde.getCover()));
            recorde.setId(key.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateById(Recorde recorde) {
        try {
            Db.use().update(
                    Entity.create()
                            .set("update_time", new Date())
                            .set("room_id", recorde.getRoomId())
                            .set("live_start_time", recorde.getLiveStartTime())
                            .set("live_stop_time", recorde.getLiveStopTime()).set("title", recorde.getTitle())
                            .set("cover", recorde.getCover()),
                    Entity.create("recorde").set("id", recorde.getId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Recorde> getNotReleaseList() {
        try {
            List<Recorde> recordeNotList = Db.use().query("select * from recorde where success = false", Recorde.class);//未发布的录制事件
            DateTime currentDate = DateUtil.offsetMinute(DateUtil.date(), -10);//当前时间

            List<Recorde> recordeList = recordeNotList.stream().filter(recorde -> {
                if (recorde.getLiveStopTime() != null) {
                    //视频文件已结束
                    if (currentDate.after(recorde.getLiveStopTime())) {
                        //视频结束时间超过十分钟进行发布
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            return recordeList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public PageResult<Entity> getRecordeList(Integer page, Integer pageSize) {
        try {
            PageResult<Entity> result = Db.use().page(Entity.create("recorde"), new Page(page, pageSize));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult<>();
        }
    }
}

