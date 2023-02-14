package com.tricycle.up.framework;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.db.Db;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.ds.GlobalDSFactory;
import cn.hutool.setting.Setting;
import com.tricycle.up.config.Config;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/14 2:36
 * @description
 */
@Slf4j
public class DBInit extends Init {

    private static final String DB_NAME = Config.PATH + "bili.db";

    @Override
    public void start() {
        initDb();
        initDataSource();

        initTable("live");
        initTable("recorde");
        initTable("user");
        initTable("video");
    }

    /**
     * 初始化数据库连接
     */
    private void initDataSource() {
        Setting setting = Setting.create();
        setting.put("url", "jdbc:sqlite:" + DB_NAME);
        setting.put("driver", "org.sqlite.JDBC");
//        setting.put("showSql", "true");
//        setting.put("showParams", "true");
        GlobalDSFactory.set(DSFactory.create(setting));

        log.info("创建数据源成功");
    }

    /**
     * 初始化数据库
     */
    private void initDb() {
        if (!FileUtil.exist(DB_NAME)) {
            FileUtil.touch(DB_NAME);
            log.info("数据库初始化成功");
        }
    }

    /**
     * 初始化表
     *
     * @param tableName
     * @throws SQLException
     */
    private void initTable(String tableName) {
        try {
            Db.use().query("select * from " + tableName);
        } catch (SQLException e) {
            //初始化
            ClassPathResource resource = new ClassPathResource("sql/" + tableName + ".sql");
            try {
                Db.use().execute(resource.readUtf8Str());
                log.info("{}表初始化成功", tableName);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
