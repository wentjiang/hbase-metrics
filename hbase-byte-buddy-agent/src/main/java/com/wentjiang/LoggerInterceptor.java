package com.wentjiang;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
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
        System.out.println(method.toString());
        System.out.println(args.toString());
        Object returnValue = "aa";
        zuper.call();
        method.invoke(self, args);
        System.out.println(returnValue.toString());
        return returnValue;
    }

}
