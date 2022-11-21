package com.wentjiang.agent;

import com.wentjiang.agent.InstanceMethodsAroundInterceptor;
import com.wentjiang.agent.common.InstMethodsInter;
import com.wentjiang.agent.impl.HRegionDoWALAppendInterceptor;
import com.wentjiang.agent.impl.MockReadHBaseCacheInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.wentjiang.agent.util.AgentUtils.transferMethodInfo2ElementMatcher;
import static net.bytebuddy.matcher.ElementMatchers.*;

public class HBaseByteBuddyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        // System.out.println("premain in HBaseByteBuddyAgent");

        new AgentBuilder.Default().type(ElementMatchers.nameContainsIgnoreCase("HMaster"))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                    System.out.println("start transform!");
                    return builder.method(named("run"))
                            .intercept(MethodDelegation.withDefaultConfiguration().to(new InstMethodsInter(
                                    "com.wentjiang.agent.impl.MockReadHBaseCacheInterceptor", classLoader)));
                }).with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                // .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .installOn(inst);

        // for (InstanceMethodsAroundInterceptor interceptor : getInterceptorList()) {
        // enhanceClass(interceptor, inst);
        // }

        // System.out.println("premain in HBaseByteBuddyAgent done");
    }

    public static List<InstanceMethodsAroundInterceptor> getInterceptorList() {
        return Arrays.asList(
                // new HRegionDoWALAppendInterceptor(),
                new MockReadHBaseCacheInterceptor());
    }

    public static void enhanceClass(InstanceMethodsAroundInterceptor interceptor, Instrumentation inst) {
        new AgentBuilder.Default().type(ElementMatchers.named(interceptor.getEnhanceClassName()))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> builder
                        .method(transferMethodInfo2ElementMatcher(interceptor.getEnhanceMethodInfo()))
                        .intercept(MethodDelegation.withDefaultConfiguration()
                                .to(new InstMethodsInter(interceptor.getClass().getName(), classLoader))))
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION).installOn(inst);
    }
}
