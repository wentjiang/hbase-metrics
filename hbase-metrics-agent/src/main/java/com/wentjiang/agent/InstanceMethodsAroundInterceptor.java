package com.wentjiang.agent;

import com.wentjiang.agent.common.MethodInfo;
import com.wentjiang.agent.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;

import static com.wentjiang.agent.util.LogUtil.*;

public class InstanceMethodsAroundInterceptor {
    private final Logger logger = LoggerFactory.getLogger(InstanceMethodsAroundInterceptor.class);
    private final String className;
    private Class clazz;
    private boolean isReady = false;

    public InstanceMethodsAroundInterceptor(String className) {
        this.className = className;
        try {
            clazz = Class.forName(className);
            isReady = true;
        } catch (ClassNotFoundException e) {
            logger.warn(className + " not found");
        }
    }

    public void beforeMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, String requestId)
            throws Throwable {
        String metricJson = LogUtil.recordMetrics(clazz, method, OPERATE_BEGIN, requestId, new HashMap<>());
        System.out.println("metricJson: " + metricJson);
        logger.info("metricJson: " + metricJson);
    }

    public Object afterMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret,
            String requestId) throws Throwable {
        String metricJson = LogUtil.recordMetrics(clazz, method, OPERATE_END, requestId, new HashMap<>());
        System.out.println("metricJson: " + metricJson);
        logger.info("metricJson: " + metricJson);
        return ret;
    }

    public void handleMethodException(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t,
            String requestId) {
        String metricJson = LogUtil.recordMetrics(clazz, method, OPERATE_EXCEPTION, requestId, new HashMap<>());
        System.out.println("exception metricJson: " + metricJson);
        logger.error("exception metricJson: " + metricJson);
    }

    public String getEnhanceClassName() {
        return className;
    }

    public MethodInfo getEnhanceMethodInfo() {
        throw new IllegalStateException("should implement in subclass");
    }

    public boolean isReady() {
        return isReady;
    }
}
