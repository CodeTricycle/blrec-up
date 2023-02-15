package com.tricycle.up.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;
import com.tricycle.up.framework.CommonPage;
import com.tricycle.up.mapper.RecordeMapper;
import com.tricycle.up.mapper.VideoMapper;
import com.tricycle.up.service.RecordeService;
import com.tricycle.up.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/16 3:19
 * @description
 */
@Service
public class RecordeServiceImpl extends ServiceImpl<RecordeMapper, Recorde> implements RecordeService {

    @Autowired
    private VideoService videoService;

    @Override
    public Recorde getLastByRoomId(long roomId) {
        LambdaQueryWrapper<Recorde> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Recorde::getRoomId, roomId);
        return this.getOne(wrapper);
    }

    @Override
    public List<Recorde> getNotReleaseList() {
        LambdaQueryWrapper<Recorde> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Recorde::isSuccess, false);
        List<Recorde> recordeNotList = this.baseMapper.selectList(wrapper);

        DateTime currentDate = DateUtil.offsetMinute(DateUtil.date(), -10);//当前时间

        List<Recorde> recordeList = recordeNotList.stream().filter(recorde -> {
            if (recorde.getLiveStopTime() != null) {
                //视频文件已结束
                if (currentDate.after(recorde.getLiveStopTime())) {
                    //视频结束时间超过十分钟
                    //判断文件是否全部上传完成
                    Long count = videoService.getNotUploadList(recorde.getId());
                    if (count.equals(0))
                        return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        return recordeList;
    }

    @Override
    public CommonPage getRecordeList(Integer currentPage, Integer pageSize) {
        Page<Recorde> page = new Page<>(currentPage, pageSize);
        page = this.page(page);
        return new CommonPage(page.getTotal(), currentPage, pageSize, page.getRecords());
    }
}
