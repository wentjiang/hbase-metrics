package com.wentjiang;

import java.lang.reflect.Method;

public interface InstanceMethodsAroundInterceptor {
    /**
     * called before target method invocation.
     *
     * @param result change this result, if you want to truncate the method.
     * @throws Throwable
     */
    void beforeMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes) throws Throwable;

    /**
     * called after target method invocation. Even method's invocation triggers an exception.
     *
     * @param method
     * @param ret the method's original return value.
     * @return the method's actual return value.
     * @throws Throwable
     */
    Object afterMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                       Object ret) throws Throwable;

    /**
     * called when occur exception.
     *
     * @param method
     * @param t the exception occur.
     */
    void handleMethodException(Method method, Object[] allArguments,
                               Class<?>[] argumentsTypes,
                               Throwable t);
}
