package com.tricycle.up.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/13 11:23
 * @description
 */
public class EventUtil {

    private static Map<Long, Lock> lockMap = new HashMap<>();//用于管理房间录制文件顺序的问题
    public static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    /**
     * 新建锁，开始录制的时候调用
     * @param roomId
     */
    public static void addLock(Long roomId) {
        Lock lock = new ReentrantLock(true);//公平锁
        lockMap.put(roomId, lock);
    }

    /**
     * 上锁，文件完成、文件新建调用
     * @param roomId
     */
    public static void lock(Long roomId) {
        Lock lock = lockMap.get(roomId);
        if (Objects.isNull(lock)){
            lock = new ReentrantLock(true);//公平锁
            lockMap.put(roomId, lock);
        }
        lock.lock();//上锁
    }

    /**
     * 解锁，文件完成、文件新建调用
     * @param roomId
     */
    public static void unlock(Long roomId) {
        Lock lock = lockMap.get(roomId);
        if (Objects.isNull(lock)) {
            return;
        }
        lock.unlock();//解锁
    }

    /**
     * 移除锁，停止录制的时候调用
     * @param roomId
     */
    public static void removeLock(Long roomId) {
        lockMap.remove(roomId);
    }

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
        recorde.setLiveStartTime(null);
        recorde.setLiveStopTime(null);
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
        video.setRoomId(data.getLong("room_id"));

        return video;
    }
}
