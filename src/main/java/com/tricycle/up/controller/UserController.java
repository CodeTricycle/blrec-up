package com.tricycle.up.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.tricycle.up.entity.QRCode;
import com.tricycle.up.entity.User;
import com.tricycle.up.framework.Result;
import com.tricycle.up.service.UserService;
import com.tricycle.up.util.BiliUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/7 3:41
 * @description 登录功能
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取登录二维码
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/getQRCode")
    public void getQRCode(HttpServletResponse response) throws IOException {
        QRCode qrCode = BiliUtil.getQRCode();
        if (Objects.isNull(qrCode))
            return;
        QrCodeUtil.generate(qrCode.getUrl(), 300, 300, "JPG", response.getOutputStream());

        ThreadUtil.execAsync(() -> {
            for (int i = 0; i < 18; i++) {
                ThreadUtil.sleep(10 * 1000);//间隔十秒进行一次获取
                User loginUser = BiliUtil.login(qrCode.getAuthCode());
                if (loginUser != null) {
                    loginUser.setCookie(
                            StrUtil.format("SESSDATA={}; bili_jct={}; DedeUserID={}; DedeUserID__ckMd5={};",
                                    loginUser.getSessData(), loginUser.getBiliJct(), loginUser.getDedeUserId(), loginUser.getCkMd5()
                            ));
                    User user = userService.getUserByUserId(loginUser.getDedeUserId());
                    if (Objects.isNull(user)) {
                        userService.save(loginUser);
                    } else {
                        BeanUtil.copyProperties(loginUser, user, "id", "create_time", "update_time");
                        userService.updateById(user);
                    }
                    break;//登录成功
                }
            }
        });
    }

    @GetMapping("/getUserList")
    public Result getUserList() {
        return Result.getSucc(userService.list());
    }

    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Integer id) {
        userService.removeById(id);
        return Result.getSucc();
    }

    @GetMapping("/updateById")
    public Result updateById(@RequestBody User user) {
        userService.updateById(user);
        return Result.getSucc();
    }
}
