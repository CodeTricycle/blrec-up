package com.tricycle.up.controller;

import cn.hutool.core.util.StrUtil;
import com.tricycle.up.framework.Result;
import com.tricycle.up.util.EventUtil;
import lombok.extern.slf4j.Slf4j;
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
    public Result webHook(@RequestBody(required = false) String json){
        try {
            if (StrUtil.isNotBlank(json)) {
                EventUtil.queue.put(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.getSucc();
    }
}
