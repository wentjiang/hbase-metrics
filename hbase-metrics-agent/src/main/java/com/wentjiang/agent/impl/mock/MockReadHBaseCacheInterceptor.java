package com.wentjiang.agent.impl.mock;

import com.wentjiang.agent.InstanceMethodsAroundInterceptor;
import com.wentjiang.agent.common.MethodInfo;

import java.util.Arrays;

public class MockReadHBaseCacheInterceptor extends InstanceMethodsAroundInterceptor {

    private static final String ENHANCE_CLASS_NAME = "com.wentjiang.mockhbase.model.HMaster";

    public MockReadHBaseCacheInterceptor() {
        super(ENHANCE_CLASS_NAME);
    }

    @Override
    public MethodInfo getEnhanceMethodInfo() {
        return MethodInfo.builder().methodName("getRegion")
                .paramNames(Arrays.asList("com.wentjiang.mockhbase.model.RegionInfo", "java.lang.String")).build();
    }
}
