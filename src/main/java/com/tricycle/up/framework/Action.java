package com.tricycle.up.framework;

import java.lang.annotation.*;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/12 21:42
 * @description
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
    String value();
}
