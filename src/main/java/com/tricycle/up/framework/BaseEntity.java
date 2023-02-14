package com.tricycle.up.framework;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 19:23
 * @description
 */
@Data
public class BaseEntity {
    private int id;

    private Date createTime;
    private Date updateTime;
}
