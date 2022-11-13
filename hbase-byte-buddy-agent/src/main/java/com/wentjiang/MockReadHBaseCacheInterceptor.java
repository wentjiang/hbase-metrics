package com.wentjiang;


import java.lang.reflect.Method;
import java.util.Arrays;

public class MockReadHBaseCacheInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, String requestId) {
        long startTime = System.currentTimeMillis();
        System.out.println("before method, the arguments input is: " + Arrays.toString(allArguments) + " requestId: " + requestId + " startTime: " + startTime);
    }

    @Override
    public Object afterMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret, String requestId) {
        long endTime = System.currentTimeMillis();
        System.out.println("return: " + ret.toString() + " requestId: " + requestId + "endTime: " + endTime);
        return ret;
    }

    @Override
    public void handleMethodException(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t, String requestId) {

    }
}
