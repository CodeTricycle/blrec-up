package com.tricycle.up.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 9:04
 * @description
 */
@Configuration
@Slf4j
public class Config {
    public static String DB_PATH = "rec" + File.separator + "bili.db";//数据库路径

    public static String APP_KEY = "4409e2ce8ffd12b8";
    public static String APP_SEC = "59b43e04ad6965f34319062b478f83dd";

    public static int CHUNK_SIZE = 10 * 1024 * 1024;//10m

}

