package com.tricycle.up.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.json.JSONUtil;
import com.tricycle.up.entity.QRCode;
import com.tricycle.up.entity.User;
import com.tricycle.up.framework.Action;
import com.tricycle.up.framework.Result;
import com.tricycle.up.mapper.UserMapper;
import com.tricycle.up.util.BiliUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 3:41
 * @description 登录功能
 */
@Slf4j
@Action("/user")
public class UserController {
    private UserMapper userMapper = Singleton.get(UserMapper.class);

    /**
     * 获取登录二维码
     *
     * @param response
     * @throws IOException
     */
    @Action("/getQRCode")
    public void getQRCode(HttpServerResponse response) {
        QRCode qrCode = BiliUtil.getQRCode();
        if (Objects.isNull(qrCode))
            return;
        QrCodeUtil.generate(qrCode.getUrl(), 300, 300, "JPG", response.getOut());

        ThreadUtil.execAsync(() -> {
            for (int i = 0; i < 18; i++) {
                ThreadUtil.sleep(10 * 1000);//间隔十秒进行一次获取
                User loginUser = BiliUtil.login(qrCode.getAuthCode());
                if (loginUser != null) {
                    loginUser.setCookie(
                            StrUtil.format("SESSDATA={}; bili_jct={}; DedeUserID={}; DedeUserID__ckMd5={};",
                                    loginUser.getSessData(), loginUser.getBiliJct(), loginUser.getDedeUserId(), loginUser.getCkMd5()
                            ));
                    User user = userMapper.getUserByUserId(loginUser.getDedeUserId());
                    if (Objects.isNull(user)) {
                        userMapper.insert(loginUser);
                    } else {
                        BeanUtil.copyProperties(loginUser, user, "id", "create_time", "update_time");
                        userMapper.updateById(user);
                    }
                    break;//登录成功
                }
            }
        });
    }

    @Action("/getUserList")
    public Result getUserList() {
        return Result.getSucc(userMapper.getUserList());
    }

    @Action("/deleteById")
    public Result deleteById(HttpServerRequest request) {
        int id = Integer.valueOf(request.getParam("id"));
        userMapper.deleteById(id);
        return Result.getSucc();
    }

    @Action("/updateById")
    public Result updateById(HttpServerRequest request) {
        String body = request.getBody();
        User user = JSONUtil.toBean(body, User.class);
        userMapper.updateById(user);
        return Result.getSucc();
    }
}
