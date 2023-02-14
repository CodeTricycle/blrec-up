package com.tricycle.up.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;

import java.util.Objects;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/13 11:23
 * @description
 */
public class EventUtil {

    /**
     * 把事件转换为事件对象
     *
     * @param object
     * @return
     */
    public static Live toLiveEvent(JSONObject object) {
        JSONObject data = object.getJSONObject("data");
        JSONObject room = data.getJSONObject("room_info");
        Live live = JSONUtil.toBean(room, Live.class);

        JSONObject user = data.getJSONObject("user_info");
        if (Objects.nonNull(user)) {
            live.setName(user.getStr("name"));
        }

        return live;
    }

    /**
     * 把事件转换为事件对象
     *
     * @param object
     * @return
     */
    public static Recorde toRecordeEvent(JSONObject object) {
        JSONObject data = object.getJSONObject("data");
        JSONObject room = data.getJSONObject("room_info");
        Recorde recorde = JSONUtil.toBean(room, Recorde.class);

        return recorde;
    }

    /**
     * 把事件转换为事件对象
     *
     * @param object
     * @return
     */
    public static Video toVideoEvent(JSONObject object) {
        JSONObject data = object.getJSONObject("data");
        Video video = new Video();
        video.setPath(data.getStr("path"));
        video.setRoomId(data.getInt("room_id"));

        return video;
    }
}
