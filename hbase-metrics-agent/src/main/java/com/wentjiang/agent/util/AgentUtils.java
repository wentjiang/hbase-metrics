package com.wentjiang.agent.util;

import com.wentjiang.agent.common.MethodInfo;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class AgentUtils {

    public static ElementMatcher.Junction transferMethodInfo2ElementMatcher(MethodInfo methodInfo) {
        ElementMatcher.Junction any = any();
        if (methodInfo.getMethodName() != null) {
            any = any.and(named(methodInfo.getMethodName()));
        }
        if (methodInfo.getParamNames() != null) {
            // any = any.and(declaresMethod())
        }
        return any;
    }

}
