package com.wentjiang.mockhbase;

import java.lang.reflect.Method;
import java.util.UUID;

public class LogUtil {

    public static String OPERATE_BEGIN = "BEGIN";
    public static String OPERATE_END = "END";

    public static String recordMetrics(Class clazz, Method method, String operate, String uuid) {
        String className = clazz.getName();
        String methodName = method.getName();

        // todo 修改为模板字符串
        String content = "";
        // """
        // {
        // "className":"$className",
        // "methodName":"${methodName}",
        // "operate":"${operate}",
        // "timestamp":${System.currentTimeMillis()},
        // "uuid":"${uuid}"
        // }
        // """;
        return content;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
