package com.tricycle.up.controller;

import cn.hutool.core.lang.Singleton;
import cn.hutool.db.Entity;
import cn.hutool.db.PageResult;
import cn.hutool.http.server.HttpServerRequest;
import com.tricycle.up.framework.CommonPage;
import com.tricycle.up.framework.Result;
import com.tricycle.up.service.RecordeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 3:41
 * @description 历史功能
 */
@Slf4j
@RequestMapping("/recorde")
@RestController
public class RecordeController {
    @Autowired
    private RecordeService recordeService;

    @GetMapping("/getRecordeList")
    public Result getRecordeList(CommonPage commonPage) {
        commonPage = recordeService.getRecordeList(commonPage.getCurrentPage(), commonPage.getPageSize());
        return Result.getSucc(commonPage);
    }
}
