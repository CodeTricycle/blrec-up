package com.tricycle.up.entity;

import lombok.Data;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 19:52
 * @description
 */
@Data
public class Live extends BaseEntity {
    private String name;//主播名
    private long roomId;//直播间
    private String title;//标题
    private String cover;//封面
    private String tags;//标签

    private Integer tid = 171;//分区

    private String titleTemplate = "【${uname}】${yyyy年MM月dd日HH点场}${title}";
    private boolean copyright = true;//是否自制
    private String desc = "直播录像\n${uname}直播间：https://live.bilibili.com/${roomId}";//简介
    private String partTitleTemplate = "P${index}";//分p标题模板

    private boolean upload = true;//是否上传
    private String userId;
}
