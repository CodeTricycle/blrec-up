package com.tricycle.up.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.db.Entity;
import cn.hutool.db.PageResult;
import cn.hutool.http.server.HttpServerRequest;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.framework.Action;
import com.tricycle.up.framework.CommonPage;
import com.tricycle.up.framework.Result;
import com.tricycle.up.mapper.RecordeMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 3:41
 * @description 历史功能
 */
@Slf4j
@Action("/recorde")
public class RecordeController {
    private RecordeMapper recordeMapper = Singleton.get(RecordeMapper.class);

    @Action("/getRecordeList")
    public Result getRecordeList(HttpServerRequest request) {
        try {
            Integer page = Integer.valueOf(request.getParam("page"));//当前页
            Integer pageSize = Integer.valueOf(request.getParam("pageSize"));//页大小
            PageResult<Entity> pageResult = recordeMapper.getRecordeList(page, pageSize);

            CommonPage commonPage = new CommonPage(pageResult.getTotal(), pageResult.getPage(), pageResult.getPageSize(), pageResult);
            return Result.getSucc(commonPage);
        }catch (Exception e){
            e.printStackTrace();
            return Result.getSucc();
        }
    }
}
