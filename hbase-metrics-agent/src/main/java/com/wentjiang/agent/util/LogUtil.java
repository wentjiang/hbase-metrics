package com.wentjiang.agent.util;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LogUtil {

    public static String OPERATE_BEGIN = "BEGIN";
    public static String OPERATE_END = "END";
    public static String OPERATE_EXCEPTION = "EXCEPTION";

    public static String recordMetrics(Class clazz, Method method, String operate, String requestId,
            Map<String, String> map) {
        String className = clazz.getName();
        String methodName = method.getName();

        return "{" + "\"className\":\"" + className + "\"," + "\"methodName\":\"" + methodName + "\","
                + "\"operate\":\"" + operate + "\"," + "\"timestamp\":" + System.currentTimeMillis() + "\","
                + "\"requestId\":\"" + requestId + "\"";
    }

    public static String map2JsonString(Map<String, String> map) {
        return "{" + map.entrySet().stream().map(e -> "\"" + e.getKey() + "\":\"" + e.getValue() + "\"")
                .collect(Collectors.joining(",")) + "}";
    }

}
