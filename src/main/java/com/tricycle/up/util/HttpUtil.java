package com.tricycle.up.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/11 18:04
 * @description
 */
public class HttpUtil {

    /**
     * 重试执行请求
     *
     * @param request
     * @param retry   重试次数
     * @return
     */
    public static JSONObject execute(HttpRequest request, int retry) {
        for (int i = 0; i < retry; i++) {
            try {
                HttpResponse response = request.setConnectionTimeout(10 * 1000).execute();
                if (response.getStatus() == 200) {
                    //请求成功
                    return JSONUtil.parseObj(response.body());
                }
                System.out.println(request);
                System.out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
