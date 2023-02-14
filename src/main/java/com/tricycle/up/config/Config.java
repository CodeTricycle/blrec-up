package com.tricycle.up.config;

import java.io.File;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 9:04
 * @description
 */
public class Config {
    public static String PATH = System.getProperty("user.dir") + File.separator;//录制目录 "/rec/"

    public static String APP_KEY = "4409e2ce8ffd12b8";
    public static String APP_SEC = "59b43e04ad6965f34319062b478f83dd";

    public static int CHUNK_SIZE = 10 * 1024 * 1024;//10m
}
