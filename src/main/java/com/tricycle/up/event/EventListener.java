package com.tricycle.up.event;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tricycle.up.util.EventUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:12
 * @description
 */
@Slf4j
public abstract class EventListener implements Listener {

    static Map<String, EventListener> listenerMap = new HashMap<>();

    static {
        listenerMap.put("LiveBeganEvent", Singleton.get(LiveEventListener.class));//开播
        listenerMap.put("LiveEndedEvent", Singleton.get(LiveEventListener.class));//关播
        listenerMap.put("VideoFileCreatedEvent", Singleton.get(VideoFileCreatedEventListener.class));//视频文件创建
        listenerMap.put("VideoFileCompletedEvent", Singleton.get(VideoFileCompletedEventListener.class));//视频文件结束
        listenerMap.put("VideoPostprocessingCompletedEvent", Singleton.get(VideoPostprocessingCompletedEventListener.class));//视频文件后处理完成
        listenerMap.put("RecordingStartedEvent", Singleton.get(RecordingStartedEventListener.class));//录制开始
        listenerMap.put("RecordingFinishedEvent", Singleton.get(RecordingFinishedEventListener.class));//录制完成
        listenerMap.put("RecordingCancelledEvent", Singleton.get(RecordingFinishedEventListener.class));//录制取消


    }

    public static void execute() {
        ThreadUtil.execute(() -> {
            while (true) {
                try {
                    String event = EventUtil.queue.take();
                    log.info("接收到webHook消息：{}", event);
                    if (StrUtil.isBlank(event)) {
                        return;
                    }
                    JSONObject object = JSONUtil.parseObj(event);
                    String eventType = object.getStr("type");

                    EventListener listener = listenerMap.get(eventType);
                    if (listener != null) {
                        listener.execute(object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
