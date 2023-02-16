package com.tricycle.up.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/10 8:00
 * @description 录制事件
 */
@Data
public class Recorde extends BaseEntity {
    private Long roomId;//直播间
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date liveStartTime;//开播时间
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date liveStopTime;//关播时间
    private String title;//标题
    private String cover;//封面

    private Boolean success;//已发布
}
