package com.tricycle.up.event;

import cn.hutool.json.JSONObject;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 11:12
 * @description
 */
public interface Listener {
    void execute(JSONObject object) throws Exception;
}
