package com.tricycle.up.entity;

import lombok.Data;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/10 19:43
 * @description
 */
@Data
public class QRCode {
    private String url;
    private String authCode;
}
