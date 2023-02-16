package com.tricycle.up.util;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/17 0:33
 * @description
 */
public class BeanUtil {

    /**
     * 获取空值属性
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        try {
            Set<String> fieldName = new HashSet<>();
            Field[] fields = ReflectUtil.getFields(source.getClass());
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(source);
                if (Objects.isNull(value))
                    fieldName.add(field.getName());
            }
            String[] result = new String[fieldName.size()];
            return fieldName.toArray(result);
        } catch (Exception e) {
            return new String[]{};
        }
    }
}
