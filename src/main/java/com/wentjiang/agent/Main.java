package com.wentjiang.agent;

import com.wentjiang.mockhbase.MockHBase;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Main {
    public static void main(String[] args) {
        delegateTo1();
//        ByteBuddyAgent.install();
//        new ByteBuddy().subclass(MockHBase.class).method(isDeclaredBy(MockHBase.class))
//                .redefine(MockHBase.class)
//                .method(named("mockReadHBaseCache").and(takesGenericArguments(String.class)))
//                .intercept(MethodDelegation.to(LoggerInterceptor.class))
//                .make()
//                .load(MockHBase.class.getClassLoader()
//                        , ClassReloadingStrategy.fromInstalledAgent()
//                );
//
//        System.out.println(new MockHBase().mockReadHBaseCache("fileName"));
    }

    public static void delegateTo() {
        ByteBuddyAgent.install();
        new ByteBuddy()
                .subclass(MockHBase.class)
                .method(named("mockReadHBaseCache"))
                .intercept(MethodDelegation.to(LoggerInterceptor.class))
                .make()
                .load(MockHBase.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER);
        MockHBase mockHBase = new MockHBase();
        String result = mockHBase.mockReadHBaseCache("testfile");
        System.out.println(result);
    }

    public static void delegateTo1() {
        ByteBuddyAgent.install();
        new ByteBuddy()
                .redefine(MockHBase.class)
                .method(named("mockReadHBaseCache"))
                .intercept(MethodDelegation.to(LoggerInterceptor.class))
                .make()
                .load(MockHBase.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        MockHBase mockHBase = new MockHBase();
        String result = mockHBase.mockReadHBaseCache("testfile");
        System.out.println(result);
    }
}