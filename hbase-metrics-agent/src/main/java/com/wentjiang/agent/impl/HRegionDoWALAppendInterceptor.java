package com.wentjiang.agent.impl;

import com.wentjiang.agent.InstanceMethodsAroundInterceptor;
import com.wentjiang.agent.common.MethodInfo;

import java.lang.reflect.Method;

/**
 * 增强HRegion doWALAppend 方法
 */
public class HRegionDoWALAppendInterceptor extends InstanceMethodsAroundInterceptor {

    private static final String ENHANCE_CLASS_NAME = "org.apache.hadoop.hbase.regionserver.HRegion";

    @Override
    public void beforeMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, String requestId)
            throws Throwable {

    }

    @Override
    public Object afterMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret,
            String requestId) throws Throwable {
        return null;
    }

    @Override
    public void handleMethodException(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t,
            String requestId) {

    }

    @Override
    public String getEnhanceClassName() {
        return ENHANCE_CLASS_NAME;
    }

    @Override
    public MethodInfo getEnhanceMethodInfo() {
        return MethodInfo.builder().argLength(7).build();
    }
}
