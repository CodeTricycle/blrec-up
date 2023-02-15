package com.tricycle.up.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 19:23
 * @description
 */
@Data
public class BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
}
