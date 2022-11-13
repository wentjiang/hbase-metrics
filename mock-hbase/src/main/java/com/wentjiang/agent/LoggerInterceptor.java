package com.wentjiang.agent;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class LoggerInterceptor {
    //    public static String log(@SuperCall Callable<String> originalMethod){
//        System.out.println("start log at " + System.currentTimeMillis());
//        try {
//            return originalMethod.call();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }finally {
//            System.out.println("end log at " + System.currentTimeMillis());
//        }
//    }
    @RuntimeType
    public static Object intercept(
                                    @This Object self,
                                   @Origin Method method,
                                    @SuperCall Callable<?> zuper,
                                   @AllArguments Object[] args) throws Throwable {
        System.out.println("result in method: " + method.toString());
        System.out.println("result in args: " + Arrays.stream(args));
        Object result = zuper.call();

        System.out.println("result in agent: " + result);
        return result;
    }

}
