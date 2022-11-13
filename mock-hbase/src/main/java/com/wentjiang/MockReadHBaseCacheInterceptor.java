package com.wentjiang;


import java.lang.reflect.Method;

public class MockReadHBaseCacheInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes) throws Throwable {
        System.out.println("before method");
    }

    @Override
    public Object afterMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        System.out.println("return: " + ret.toString() );
        return ret;
    }

    @Override
    public void handleMethodException(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }
}
