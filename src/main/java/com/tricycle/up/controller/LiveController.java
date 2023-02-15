package com.tricycle.up.controller;

import com.tricycle.up.entity.Live;
import com.tricycle.up.framework.Result;
import com.tricycle.up.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/12 22:17
 * @description
 */
@RestController
@RequestMapping("/live")
public class LiveController {
    @Autowired
    private LiveService liveService;

    @GetMapping("/getLiveList")
    public Result getLiveList() {
        return Result.getSucc(liveService.list());
    }

    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Integer id) {
        liveService.removeById(id);
        return Result.getSucc();
    }

    @PostMapping("/updateById")
    public Result updateById(@RequestBody Live live) {
        liveService.updateById(live);
        return Result.getSucc();
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody Live live) {
        liveService.save(live);
        return Result.getSucc();
    }
}
