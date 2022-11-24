package com.wentjiang.agent.impl.mock;

import com.wentjiang.agent.InstanceMethodsAroundInterceptor;
import com.wentjiang.agent.common.MethodInfo;

public class HRegionServerInterceptor extends InstanceMethodsAroundInterceptor {

    private static final String ENHANCE_CLASS_NAME = "com.wentjiang.mockhbase.model.HRegionServer";

    public HRegionServerInterceptor() {
        super(ENHANCE_CLASS_NAME);
    }

    @Override
    public MethodInfo getEnhanceMethodInfo() {
        return MethodInfo.builder().methodName("test").build();
    }
}
