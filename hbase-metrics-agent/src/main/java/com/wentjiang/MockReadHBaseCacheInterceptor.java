package com.wentjiang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MockReadHBaseCacheInterceptor implements InstanceMethodsAroundInterceptor {
    private static Logger LOG = LoggerFactory.getLogger(MockReadHBaseCacheInterceptor.class);

    @Override
    public void beforeMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, String requestId) {
        long startTime = System.currentTimeMillis();
        LOG.info("before method, the arguments input is: " + Arrays.toString(allArguments) + " requestId: " + requestId
                + " startTime: " + startTime);
        System.out.println("before method, the arguments input is: " + Arrays.toString(allArguments) + " requestId: "
                + requestId + " startTime: " + startTime);
    }

    @Override
    public Object afterMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret,
            String requestId) {
        long endTime = System.currentTimeMillis();
        // todo 需要handle ret为null的情况
        LOG.info("return: " + ret.toString() + " requestId: " + requestId + "endTime: " + endTime);
        System.out.println("return: " + ret.toString() + " requestId: " + requestId + "endTime: " + endTime);
        return ret;
    }

    @Override
    public void handleMethodException(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t,
            String requestId) {

    }
}
