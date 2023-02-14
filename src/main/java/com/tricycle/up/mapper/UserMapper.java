package com.tricycle.up.mapper;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.tricycle.up.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 20:56
 * @description
 */
public class UserMapper {

    public List<User> getUserList(){
        try {
            return Db.use().query("select * from user ", User.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public User getUserByUserId(String userId) {
        try {
            return Db.use().query("select * from user where dede_user_id = ?", User.class, userId).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 新增数据
     *
     * @param user
     */
    public void insert(User user) {
        try {
            Long key = Db.use().insertForGeneratedKey(
                    Entity.create("user")
                            .set("create_time", new Date()).set("mid", user.getMid()).set("access_token", user.getAccessToken())
                            .set("refresh_token", user.getRefreshToken()).set("sess_data", user.getSessData()).set("bili_jct", user.getBiliJct())
                            .set("ck_md5", user.getCkMd5()).set("sid", user.getSid())
                            .set("cookie", user.getCookie()).set("dede_user_id", user.getDedeUserId())
            );
            user.setId(key.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateById(User user) {
        try {
            Db.use().update(
                    Entity.create()
                            .set("create_time", new Date()).set("mid", user.getMid()).set("access_token", user.getAccessToken())
                            .set("refresh_token", user.getRefreshToken()).set("sess_data", user.getSessData()).set("bili_jct", user.getBiliJct())
                            .set("ck_md5", user.getCkMd5()).set("sid", user.getSid())
                            .set("cookie", user.getCookie()),
                    Entity.create("user").set("id", user.getId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try {
            Db.use().del("user", "id", id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
