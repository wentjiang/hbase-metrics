package com.wentjiang;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class HBaseByteBuddyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        Class clazz = null;
        try {
            clazz = Class.forName("com.wentjiang.mockhbase.MockHBase");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("premain in HBaseByteBuddyAgent");
        System.out.println("class name: " + clazz.getSimpleName());
        ByteBuddyAgent.install();
        new ByteBuddy()
                .subclass(clazz)
                .method(named("mockReadHBaseCache"))
                .intercept(MethodDelegation.to(LoggerInterceptor.class))
                .make()
                .load(clazz.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER);
        try {
            System.out.println("before call method");
            clazz.getMethod("mockReadHBaseCache", String.class).invoke(clazz.newInstance(),"filename1");
            System.out.println("after call method");
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        System.out.println("premain in HBaseByteBuddyAgent done");
    }
}
