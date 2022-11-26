package com.wentjiang.agent.impl.test;

import com.wentjiang.agent.InstanceMethodsAroundInterceptor;
import com.wentjiang.agent.common.MethodInfo;

public class FSWALTestInterceptor extends InstanceMethodsAroundInterceptor {

    private static final String ENHANCE_CLASS_NAME = "org.apache.hadoop.hbase.regionserver.wal.AbstractFSWAL";

    @Override
    public String getEnhanceClassName() {
        return ENHANCE_CLASS_NAME;
    }

    @Override
    public MethodInfo getMethodInfo() {
        return MethodInfo.builder().methodName("postSync").build();
    }
}
