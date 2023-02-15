package com.tricycle.up.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;
import com.tricycle.up.mapper.VideoMapper;
import com.tricycle.up.service.VideoService;
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
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Override
    public Video getVideoByPath(String path) {
        LambdaQueryWrapper<Video> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Video::getPath, path);

        return this.getOne(wrapper);
    }

    @Override
    public Video getLastVideoByRoomId(long roomId) {
        LambdaQueryWrapper<Video> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Video::getRoomId, roomId);

        return this.getOne(wrapper);
    }

    @Override
    public List<Video> getNotUploadList() {
        LambdaQueryWrapper<Video> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Video::getSuccess, 0)//未上传
                .ge(Video::getFileCloseTime, DateUtil.date());//大于当前时间

        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public Long getNotUploadList(Integer recordeId) {
        LambdaQueryWrapper<Video> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Video::getSuccess, 0)
                .eq(Video::getRecordeId, recordeId)
                .ge(Video::getFileCloseTime, DateUtil.date());//大于当前时间

        return this.baseMapper.selectCount(wrapper);
    }

    @Override
    public List<Video> getVideoListByRecordeId(int id) {
        LambdaQueryWrapper<Video> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Video::getRecordeId, id);

        return this.list(wrapper);
    }

    @Override
    public List<Video> getVideoListByRecordeIdAndSuccess(int id) {
        LambdaQueryWrapper<Video> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Video::getRecordeId, id);
        wrapper.eq(Video::getSuccess, true);

        return this.list(wrapper);
    }

    @Override
    public Video getVideoById(Integer id) {
        return this.baseMapper.selectById(id);
    }
}
