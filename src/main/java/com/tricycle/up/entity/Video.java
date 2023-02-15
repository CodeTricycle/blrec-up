package com.tricycle.up.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 20:37
 * @description
 */
@Data
public class Video extends BaseEntity {
    private int recordeId;//录制事件id
    private long roomId;
    private String path;//文件路径
    private int pIndex = 1;//第几分p
    private Date fileOpenTime;
    private Date fileCloseTime;

    //step 1
    private String url;
    private String filename;
    private String complete;

    //step 2
    private Integer success = 0;//0未上传 1已上传 -1出错
}
