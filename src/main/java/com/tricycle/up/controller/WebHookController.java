package com.tricycle.up.controller;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.tricycle.up.event.EventListener;
import com.tricycle.up.framework.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/16 2:57
 * @description
 */
@RestController
@Slf4j
public class WebHookController {

    @RequestMapping("/webHook")
    public Result webHook(@RequestBody(required = false) String json) {
        if (StrUtil.isNotBlank(json)) {
            log.info("接收到webHook消息：{}", json);
            EventListener.execute(json);
        }
        return Result.getSucc();
    }
}
