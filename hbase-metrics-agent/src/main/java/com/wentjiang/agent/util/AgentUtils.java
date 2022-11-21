package com.wentjiang.agent.util;

import com.wentjiang.agent.common.AgentClassLoader;
import com.wentjiang.agent.common.MethodInfo;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class AgentUtils {

    private static final Logger logger = LoggerFactory.getLogger(AgentUtils.class);

    /**
     * 通过methodInfo来选取对应的过滤方法
     * 
     * @param methodInfo
     * 
     * @return
     */
    public static ElementMatcher.Junction transferMethodInfo2ElementMatcher(MethodInfo methodInfo) {
        ElementMatcher.Junction methodNameJunction;
        if (methodInfo.getMethodName() != null) {
            methodNameJunction = named(methodInfo.getMethodName());
        } else {
            throw new IllegalStateException("should set the method name when match the method.");
        }

        ElementMatcher.Junction paramLengthJunction = any();
        if (methodInfo.getArgumentLength() != null) {
            paramLengthJunction = paramLengthJunction.and(takesArguments(methodInfo.getArgumentLength()));
        }

        ElementMatcher.Junction paramClassJunction = any();
        if (methodInfo.getParamNames() != null) {
            for (int i = 0; i < methodInfo.getParamNames().size(); i++) {
                try {
                    paramClassJunction = paramClassJunction
                            .and(takesGenericArgument(i, Class.forName(methodInfo.getParamNames().get(i))));
                } catch (ClassNotFoundException e) {
                    logger.error("The param " + i + " class" + methodInfo.getParamNames().get(i)
                            + " not found when enhance method: " + methodInfo.getMethodName());
                    throw new RuntimeException(e);
                }
            }
        }
        return methodNameJunction.and(paramLengthJunction).and(paramClassJunction);
    }

}
