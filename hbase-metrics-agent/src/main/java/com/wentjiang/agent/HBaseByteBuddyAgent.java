package com.wentjiang.agent;

import com.wentjiang.agent.InstanceMethodsAroundInterceptor;
import com.wentjiang.agent.common.InstMethodsInter;
import com.wentjiang.agent.impl.HRegionDoWALAppendInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class HBaseByteBuddyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        System.out.println("premain in HBaseByteBuddyAgent");

        new AgentBuilder.Default().type(ElementMatchers.nameContainsIgnoreCase("HMaster"))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                    System.out.println("start transform!");
                    return builder.method(named("run")).intercept(MethodDelegation.withDefaultConfiguration()
                            .to(new InstMethodsInter("com.wentjiang.MockReadHBaseCacheInterceptor", classLoader)));
                }).with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                // .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .installOn(inst);

        System.out.println("premain in HBaseByteBuddyAgent done");
    }

    public static List<InstanceMethodsAroundInterceptor> getInterceptorList() {
        return Arrays.asList(new HRegionDoWALAppendInterceptor());
    }

    public static void enhanceClass(InstanceMethodsAroundInterceptor interceptor) {
        new AgentBuilder.Default().type(ElementMatchers.named(interceptor.getEnhanceClassName()))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                    return builder.method(named("run")).intercept(MethodDelegation.withDefaultConfiguration()
                            .to(new InstMethodsInter("com.wentjiang.MockReadHBaseCacheInterceptor", classLoader)));
                }).with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                // .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .installOn(inst);
    }
}
