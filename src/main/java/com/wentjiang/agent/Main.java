package com.wentjiang.agent;

import com.wentjiang.mockhbase.MockHBase;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.MethodDelegation;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(args);
//        ByteBuddyAgent.install();
//        new ByteBuddy()
//                .redefine(MockHBase.class)
//                .method(named("mockReadHBaseCache").and(takesGenericArguments(String.class)))
//                .intercept(MethodDelegation.to(GeneralInterceptor.class))
//                .make()
//                .load(TestClass.class.getClassLoader()
//                        , ClassReloadingStrategy.fromInstalledAgent()
//                );
//
////        System.out.println(new TestClass().test("wentao"));
        System.out.println(new TestClass().test("wentao", "jiang"));
        System.out.println(new MockHBase().mockReadHBaseCache("testfile"));
    }



}