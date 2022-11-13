package com.wentjiang.agent;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

public class MetricsInterceptor {
    @RuntimeType
    public static Object intercept(
            @This Object self,
            @AllArguments Object[] allArguments) throws NoSuchMethodException {
        System.out.println("asdfasdf");
        Method method = self.getClass().getMethod("test",String.class,String.class);

        return ((TestClass)self).test("a","b");
    }
}
