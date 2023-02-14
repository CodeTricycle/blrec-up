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
        try {
            HttpResponse response = request.setConnectionTimeout(10 * 1000).execute();
            if (response.getStatus() == 200) {
                //请求成功
                return JSONUtil.parseObj(response.body());
            }
            retry--;
            if (retry > 0) {
                //请求失败
                return HttpUtil.execute(request, retry);//重试
            }
            return null;//默认值
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
