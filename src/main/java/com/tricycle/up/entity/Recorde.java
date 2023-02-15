package com.tricycle.up.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/10 8:00
 * @description 录制事件
 */
@Data
public class Recorde extends BaseEntity {
    private long roomId;//直播间
    private Date liveStartTime;//开播时间
    private Date liveStopTime;//关播时间
    private String title;//标题
    private String cover;//封面

    private boolean success = false;//已发布
}
