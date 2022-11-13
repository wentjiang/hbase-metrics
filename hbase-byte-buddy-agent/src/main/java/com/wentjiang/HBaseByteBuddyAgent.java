package com.wentjiang;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

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

        Class finalClazz = clazz;
        new AgentBuilder.Default()
                .type(ElementMatchers.is(clazz))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                    System.out.println("start transform!");
                    builder.method(named("mockReadHBaseCache"))
                            .intercept(
                                    FixedValue.value("transformed")
//                                    MethodDelegation.withDefaultConfiguration()
//                                    .to(new InstMethodsInter("com.wentjiang.MockReadHBaseCacheInterceptor", classLoader))
                            );
                    System.out.println("finish transform!");
                    return builder;
                })
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
//                .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .installOn(inst);

//        try {
//            clazz.getMethod("mockReadHBaseCache", String.class).invoke(clazz.newInstance(),"filename1");
//        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println("premain in HBaseByteBuddyAgent done");
    }
}
