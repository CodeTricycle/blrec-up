package com.tricycle.up.controller;

import cn.hutool.core.lang.Singleton;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.json.JSONUtil;
import com.tricycle.up.entity.Live;
import com.tricycle.up.framework.Action;
import com.tricycle.up.framework.Result;
import com.tricycle.up.mapper.LiveMapper;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/12 22:17
 * @description
 */
@Action("/live")
public class LiveController {
    private LiveMapper liveMapper = Singleton.get(LiveMapper.class);

    @Action("/getLiveList")
    public Result getLiveList() {
        return Result.getSucc(liveMapper.getLiveList());
    }

    @Action("/deleteById")
    public Result deleteById(HttpServerRequest request) {
        int id = Integer.valueOf(request.getParam("id"));
        liveMapper.deleteById(id);
        return Result.getSucc();
    }

    @Action("/updateById")
    public Result updateById(HttpServerRequest request) {
        String body = request.getBody();
        Live live = JSONUtil.toBean(body, Live.class);
        liveMapper.updateById(live);
        return Result.getSucc();
    }

    @Action("/insert")
    public Result insert(HttpServerRequest request) {
        String body = request.getBody();
        Live live = JSONUtil.toBean(body, Live.class);
        liveMapper.insert(live);
        return Result.getSucc();
    }
}
