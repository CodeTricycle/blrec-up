package com.tricycle.up.entity;

import lombok.Data;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/10 20:15
 * @description
 */
@Data
public class User extends BaseEntity {
    private String mid;
    private String accessToken;
    private String refreshToken;

    private String sessData;
    private String biliJct;
    private String dedeUserId;
    private String ckMd5;
    private String sid;

    private String cookie;
}
