package com.wentjiang.agent.customer.plugin;

import com.wentjiang.agent.interceptor.InstanceMethodsInterceptPoint;
import com.wentjiang.agent.interceptor.enhance.ClassEnhancePluginDefine;
import com.wentjiang.agent.match.ClassMatch;
import com.wentjiang.agent.match.NameMatch;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class MockHBaseInstrumentation extends ClassEnhancePluginDefine {

    private static final String ENHANCE_CLASS = "com.wentjiang.Mockhbase";
    private static final String ENHANCE_METHOD = "mockReadHBaseCache";
    private static final String INTERCEPT_CLASS = "com.wentjiang.agent.customer.plugin.MockReadHBaseCacheInterceptor";


    @Override
    protected ClassMatch enhanceClass() {
        return NameMatch.byName(ENHANCE_CLASS);
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named(ENHANCE_METHOD);
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return INTERCEPT_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }
}
