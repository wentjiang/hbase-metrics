package com.wentjiang;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class HBaseByteBuddyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        System.out.println("premain in HBaseByteBuddyAgent");

        new AgentBuilder.Default()
                .type(ElementMatchers.nameContainsIgnoreCase("MockHBase"))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                    System.out.println("start transform!");
                    return builder.method(named("mockReadHBaseCache"))
                            .intercept(
                                    MethodDelegation.withDefaultConfiguration()
                                    .to(new InstMethodsInter("com.wentjiang.MockReadHBaseCacheInterceptor", classLoader))
                            );
                })
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
//                .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .installOn(inst);

        System.out.println("premain in HBaseByteBuddyAgent done");
    }
}
