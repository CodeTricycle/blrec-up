package com.tricycle.up.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 20:37
 * @description
 */
@Data
public class Video extends BaseEntity {
    private Integer recordeId;//录制事件id
    private Long roomId;
    private String path;//文件路径
    private Integer pIndex;//第几分p
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fileOpenTime;
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fileCloseTime;

    //step 1
    private String url;
    private String filename;
    private String complete;

    //step 2
    private Integer success;//0未上传 1已上传 -1出错
}
