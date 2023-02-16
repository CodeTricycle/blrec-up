package com.tricycle.up.task;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.tricycle.up.config.Config;
import com.tricycle.up.entity.Recorde;
import com.tricycle.up.entity.Video;
import com.tricycle.up.service.RecordeService;
import com.tricycle.up.service.VideoService;
import com.tricycle.up.util.BiliUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.List;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/16 3:14
 * @description 项目初始化运行
 */
@Component
@Slf4j
public class InitTask {

    @Autowired
    private RecordeService recordeService;
    @Autowired
    private VideoService videoService;

    static {
        //创建数据库文件
        InitTask.initDb();
    }

    @PostConstruct
    public void initTable() {
        //创建表
        this.initTable("live");
        this.initTable("recorde");
        this.initTable("user");
        this.initTable("video");

        this.upload();
        this.release();
    }

    /**
     * 初始化数据库
     */
    private static void initDb() {
        if (!FileUtil.exist(Config.DB_PATH)) {
            FileUtil.touch(Config.DB_PATH);
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
        JdbcTemplate jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
        ClassPathResource resource = new ClassPathResource("sql/" + tableName + ".sql");
        String sql = resource.readUtf8Str();
        jdbcTemplate.execute(sql);
    }

    //@Scheduled(cron = "0 */1 * * * ?")
    public void upload() {
        log.info("触发任务-上传文件");
        List<Video> videoList = videoService.getNotUploadList();
        videoList.forEach(video -> {
            BiliUtil.uploadService.submit(new UploadTask(video));
        });
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void release() {
        log.info("触发任务-发布投稿");
        List<Recorde> releaseList = recordeService.getNotReleaseList();
        releaseList.forEach(recorde -> {
            new ReleaseTask(recorde).run();
            ThreadUtil.sleep(35 * 1000);//间隔35秒
        });
    }
}
