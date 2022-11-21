package com.wentjiang.agent;

import com.wentjiang.agent.common.MethodInfo;

import java.lang.reflect.Method;
import java.util.UUID;

public abstract class InstanceMethodsAroundInterceptor {

    public abstract void beforeMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, String requestId)
            throws Throwable;

    public abstract Object afterMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret,
            String requestId) throws Throwable;

    public abstract void handleMethodException(Method method, Object[] allArguments, Class<?>[] argumentsTypes,
            Throwable t, String requestId);

    public abstract String getEnhanceClassName();

    public abstract MethodInfo getEnhanceMethodInfo();
}
