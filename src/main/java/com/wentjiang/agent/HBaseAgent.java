package com.wentjiang.agent;

import com.wentjiang.agent.customer.plugin.MockHBaseInstrumentation;
import com.wentjiang.agent.interceptor.enhance.AbstractClassEnhancePluginDefine;
import com.wentjiang.agent.plugin.EnhanceContext;
import com.wentjiang.mockhbase.MockHBase;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.nameContains;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

public class HBaseAgent {
    private static final Logger logger = LoggerFactory.getLogger(HBaseAgent.class);
    static boolean IS_OPEN_DEBUGGING_CLASS = false;

    public static void premain(String agentArgs, Instrumentation instrumentation) throws IOException {
        final ByteBuddy byteBuddy = new ByteBuddy()
                .with(TypeValidation.of(IS_OPEN_DEBUGGING_CLASS));

        AgentBuilder agentBuilder = new AgentBuilder.Default(byteBuddy)
                .ignore(
                        nameStartsWith("net.bytebuddy.")
                                .or(nameStartsWith("org.slf4j."))
                                .or(nameStartsWith("org.groovy."))
                                .or(nameContains("javassist"))
                                .or(nameContains(".asm."))
                                .or(nameStartsWith("sun.reflect"))
                                .or(ElementMatchers.<TypeDescription>isSynthetic()));

        ElementMatcher<? super TypeDescription> matcher = ElementMatchers.isDeclaredBy(MockHBase.class);

        agentBuilder
                .type(matcher)
                .transform(new Transformer())
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .with(new Listener())
                .installOn(instrumentation);
    }

    private static class Transformer implements AgentBuilder.Transformer {

        Transformer() {
        }

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, ProtectionDomain protectionDomain) {
            List<AbstractClassEnhancePluginDefine> pluginDefines = List.of(new MockHBaseInstrumentation());
            EnhanceContext context = new EnhanceContext();
            DynamicType.Builder<?> newBuilder = builder;
            for (AbstractClassEnhancePluginDefine define : pluginDefines){
                DynamicType.Builder<?> customerBuilder = define.define(typeDescription, builder, classLoader, context);
                if (customerBuilder != null) {
                    newBuilder = customerBuilder;
                }
            }
            return newBuilder;
        }
    }

    private static class Listener implements AgentBuilder.Listener {
        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }

        @Override
        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                                     boolean loaded, DynamicType dynamicType) {
            logger.info("On Transformation class {}.", typeDescription.getName());
        }

        @Override
        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                              boolean loaded) {

        }

        @Override
        public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded,
                            Throwable throwable) {
            logger.error("Enhance class " + typeName + " error.", throwable);
        }

        @Override
        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
        }
    }
}


