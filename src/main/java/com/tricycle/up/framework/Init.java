package com.tricycle.up.framework;


/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/13 10:32
 * @description
 */
public abstract class Init {

    public static void init() throws Exception {
        new DBInit().start();
        new ScheduleInit().start();
        new ServerInit().start();
    }

    public abstract void start() throws Exception;
}
