package com.wentjiang.agent;

import com.wentjiang.agent.common.InstMethodsInter;
import com.wentjiang.agent.impl.HRegionDoWALAppendInterceptor;
import com.wentjiang.agent.impl.mock.MockHRegionServerInterceptor;
import com.wentjiang.agent.impl.mock.MockReadHBaseCacheInterceptor;
import com.wentjiang.agent.impl.test.FSWALTestInterceptor;
import com.wentjiang.agent.util.AgentUtils;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class HBaseByteBuddyAgent {

    private static final Logger logger = LoggerFactory.getLogger(HBaseByteBuddyAgent.class);
    private static final List<InstanceMethodsAroundInterceptor> interceptors = Arrays.asList(
            new HRegionDoWALAppendInterceptor(), new MockReadHBaseCacheInterceptor(),
            new MockHRegionServerInterceptor(), new FSWALTestInterceptor());

    public static void premain(String agentArgs, Instrumentation inst) {
        for (InstanceMethodsAroundInterceptor interceptor : interceptors) {
            logger.info("start load class: " + interceptor.getEnhanceClassName() + " interceptor.");
            enhanceClass(interceptor, inst);
        }
        logger.info("load agent interceptor finished");
    }

    public static void enhanceClass(InstanceMethodsAroundInterceptor interceptor, Instrumentation inst) {
        new AgentBuilder.Default().type(named(interceptor.getEnhanceClassName()))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> builder
                        .method(AgentUtils.transferMethodInfo2ElementMatcher(interceptor.getMethodInfo()))
                        .intercept(MethodDelegation.withDefaultConfiguration()
                                .to(new InstMethodsInter(interceptor.getClass().getName(), classLoader))))
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION).installOn(inst);
    }
}
