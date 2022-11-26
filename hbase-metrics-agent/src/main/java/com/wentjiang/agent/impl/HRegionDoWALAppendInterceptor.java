package com.wentjiang.agent.impl;

import com.wentjiang.agent.InstanceMethodsAroundInterceptor;
import com.wentjiang.agent.common.MethodInfo;

/**
 * 增强HRegion doWALAppend 方法
 */
public class HRegionDoWALAppendInterceptor extends InstanceMethodsAroundInterceptor {

    private static final String ENHANCE_CLASS_NAME = "org.apache.hadoop.hbase.regionserver.HRegion";

    @Override
    public String getEnhanceClassName() {
        return ENHANCE_CLASS_NAME;
    }

    @Override
    public MethodInfo getMethodInfo() {
        return MethodInfo.builder().methodName("doWALAppend").build();
    }
}
