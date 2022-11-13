package com.wentjiang.agent.customer.plugin;

import com.wentjiang.agent.plugin.EnhancedInstance;
import com.wentjiang.agent.plugin.InstanceMethodsAroundInterceptor;
import com.wentjiang.agent.plugin.MethodInterceptResult;

import java.lang.reflect.Method;

public class MockReadHBaseCacheInterceptor1 implements InstanceMethodsAroundInterceptor {
    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {
        System.out.println("before method");
    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        System.out.println("return: " + ret.toString() );
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }
}
