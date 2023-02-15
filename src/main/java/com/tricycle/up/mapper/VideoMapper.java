package com.tricycle.up.mapper;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 20:56
 * @description
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

}
