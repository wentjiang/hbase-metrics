package com.wentjiang.agent.impl;

import com.wentjiang.agent.InstanceMethodsAroundInterceptor;
import com.wentjiang.agent.common.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MockReadHBaseCacheInterceptor extends InstanceMethodsAroundInterceptor {
    private static Logger logger = LoggerFactory.getLogger(MockReadHBaseCacheInterceptor.class);

    @Override
    public void beforeMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, String requestId) {
        long startTime = System.currentTimeMillis();
        logger.info("before method, the arguments input is: " + Arrays.toString(allArguments) + " requestId: "
                + requestId + " startTime: " + startTime);
        System.out.println("before method, the arguments input is: " + Arrays.toString(allArguments) + " requestId: "
                + requestId + " startTime: " + startTime);
    }

    @Override
    public Object afterMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret,
            String requestId) {
        long endTime = System.currentTimeMillis();
        // todo 需要handle ret为null的情况
        logger.info("return: " + ret.toString() + " requestId: " + requestId + "endTime: " + endTime);
        System.out.println("return: " + ret.toString() + " requestId: " + requestId + "endTime: " + endTime);
        return ret;
    }

    @Override
    public void handleMethodException(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t,
            String requestId) {

    }

    @Override
    public String getEnhanceClassName() {
        return "com.wentjiang.mockhbase.model.HMaster";
    }

    @Override
    public MethodInfo getEnhanceMethodInfo() {
        return MethodInfo.builder().methodName("getRegion")
                .paramNames(Arrays.asList("com.wentjiang.mockhbase.model.RegionInfo", "java.lang.String")).build();
    }
}
