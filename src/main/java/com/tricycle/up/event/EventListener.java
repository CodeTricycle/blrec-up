package com.tricycle.up.event;

import cn.hutool.core.lang.Singleton;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:12
 * @description
 */
public abstract class EventListener implements Listener {

    static Map<String, EventListener> listenerMap = new HashMap<>();

    static {
        listenerMap.put("LiveBeganEvent", Singleton.get(LiveEventListener.class));//开播
        listenerMap.put("LiveEndedEvent", Singleton.get(LiveEventListener.class));//关播
        listenerMap.put("VideoFileCreatedEvent", Singleton.get(VideoFileCreatedEventListener.class));//视频文件创建
        listenerMap.put("VideoPostprocessingCompletedEvent", Singleton.get(VideoPostprocessingCompletedEventListener.class));//视频文件后处理完成
        listenerMap.put("RecordingStartedEvent", Singleton.get(RecordingStartedEventListener.class));//录制开始
        listenerMap.put("RecordingFinishedEvent", Singleton.get(RecordingFinishedEventListener.class));//录制完成
        listenerMap.put("RecordingCancelledEvent", Singleton.get(RecordingFinishedEventListener.class));//录制取消
    }

    public static void execute(String event) {
        try {
            JSONObject object = JSONUtil.parseObj(event);
            String eventType = object.getStr("type");

            EventListener listener = listenerMap.get(eventType);
            if (listener != null) {
                listener.execute(object);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
