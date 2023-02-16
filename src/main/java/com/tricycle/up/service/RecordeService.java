package com.tricycle.up.service;

import cn.hutool.db.Entity;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.framework.CommonPage;

import java.util.List;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/16 3:19
 * @description
 */
public interface RecordeService extends IService<Recorde> {
    Recorde getLastRecordeByRoomId(long roomId);
    List<Recorde> getNotReleaseList();
    CommonPage getRecordeList(Integer page, Integer pageSize);
}
