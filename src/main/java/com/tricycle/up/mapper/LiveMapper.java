package com.tricycle.up.mapper;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tricycle.up.entity.Live;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 17:53
 * @description
 */
@Mapper
public interface LiveMapper extends BaseMapper<Live> {

}

