package com.wentjiang.agent;

import com.wentjiang.agent.common.InstMethodsInter;
import com.wentjiang.agent.impl.HRegionDoWALAppendInterceptor;
import com.wentjiang.agent.impl.mock.HRegionServerInterceptor;
import com.wentjiang.agent.impl.mock.MockReadHBaseCacheInterceptor;
import com.wentjiang.agent.impl.test.FSWALTestInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wentjiang.agent.util.AgentUtils.transferMethodInfo2ElementMatcher;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class HBaseByteBuddyAgent {

    private static final Logger logger = LoggerFactory.getLogger(HBaseByteBuddyAgent.class);
    private static final List<InstanceMethodsAroundInterceptor> interceptors = Arrays.asList(
            // new HRegionDoWALAppendInterceptor(),
            new MockReadHBaseCacheInterceptor()
    // new FSWALTestInterceptor()
    // new HRegionServerInterceptor()
    );

    public static void premain(String agentArgs, Instrumentation inst) {
        // for (InstanceMethodsAroundInterceptor interceptor : getInterceptorList()) {
        // enhanceClass(interceptor, inst);
        // }
        new AgentBuilder.Default().type(named("com.wentjiang.mockhbase.model.HMaster"))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> builder
                        .method(named("getRegion"))
                        .intercept(MethodDelegation.withDefaultConfiguration()
                                .to(new InstMethodsInter(MockReadHBaseCacheInterceptor.class.getName(), classLoader))))
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION).installOn(inst);
        logger.info("load agent interceptor finished");
    }

    public static List<InstanceMethodsAroundInterceptor> getInterceptorList() {
        List<InstanceMethodsAroundInterceptor> interceptorsNeedLoad = new ArrayList<>();
        for (InstanceMethodsAroundInterceptor interceptor : interceptors) {
            if (interceptor.isReady()) {
                logger.info("add " + interceptor.getClass().getName() + " into need load interceptors list");
                interceptorsNeedLoad.add(interceptor);
            }
        }
        return interceptorsNeedLoad;
    }

    public static void enhanceClass(InstanceMethodsAroundInterceptor interceptor, Instrumentation inst) {
        new AgentBuilder.Default().type(named("com.wentjiang.mockhbase.model.HMaster"))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> builder
                        .method(named("getRegion"))
                        .intercept(MethodDelegation.withDefaultConfiguration()
                                .to(new InstMethodsInter(interceptor.getClass().getName(), classLoader))))
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION).installOn(inst);
    }
}
