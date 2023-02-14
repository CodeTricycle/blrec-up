package com.tricycle.up.framework;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.http.server.SimpleServer;
import cn.hutool.json.JSONUtil;
import com.tricycle.up.config.Config;
import com.tricycle.up.event.EventListener;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/14 2:36
 * @description 初始化服务器
 */
@Slf4j
public class ServerInit extends Init {

    @Override
    public void start() throws Exception {
        SimpleServer server = HttpUtil.createServer(8888);
        addAction(server);
        server.setRoot(Config.PATH + "web");//设置资源访问路径
        server.start();
    }

    private static void addAction(SimpleServer server) {
        server.addAction("/webHook", (request, response) -> {
            String body = request.getBody();
            log.info("接收到webHook消息：{}", body);
            EventListener.execute(body);
            response.write("{\"id\": 1, \"msg\": \"OK\"}", ContentType.JSON.toString());
            response.sendOk();
        });

        Set<Class<?>> classSet = ClassUtil.scanPackage("com.tricycle.up.controller");
        classSet.forEach(clazz -> {
            Object cTemp = AnnotationUtil.getAnnotationValue(clazz, Action.class);
            if (StrUtil.isBlankIfStr(cTemp)) {
                //不是Controller直接跳过
                return;
            }
            initMethod(clazz, server, cTemp.toString());
        });
    }

    /**
     * 初始化action
     *
     * @param clazz
     * @param server
     * @param controllerPath
     */
    private static void initMethod(Class clazz, SimpleServer server, String controllerPath) {
        Object instance = Singleton.get(clazz);
        List<Method> methodList = Arrays.asList(ReflectUtil.getMethods(clazz));
        methodList.forEach(method -> {
            String mTemp = AnnotationUtil.getAnnotationValue(method, Action.class);
            if (StrUtil.isBlankIfStr(mTemp)) {
                //不是Action直接跳过
                return;
            }
            String path = controllerPath + mTemp;

            List<Class<?>> parameterTypeList = Arrays.asList(method.getParameterTypes());
            if (parameterTypeList.size() == 0) {
                server.addAction(path, (request, response) -> {
                    Object obj = ReflectUtil.invoke(instance, method.getName());
                    postHandle(response, obj);
                });
                return;
            }
            Class<?> param = parameterTypeList.get(0);
            if (param != null && param == HttpServerRequest.class) {
                server.addAction(path, (request, response) -> {
                    Object obj = ReflectUtil.invoke(instance, method.getName(), request);
                    postHandle(response, obj);
                });
            } else if (param != null && param == HttpServerResponse.class) {
                server.addAction(path, (request, response) -> {
                    Object obj = ReflectUtil.invoke(instance, method.getName(), response);
                    postHandle(response, obj);
                });
            }

        });
    }

    private static void postHandle(HttpServerResponse response, Object obj) {
        response.setContentType(ContentType.JSON.toString());
        response.setCharset(CharsetUtil.parse("UTF-8"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, content-type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (obj != null) {
            response.write(JSONUtil.toJsonStr(obj));
        }
    }


}
