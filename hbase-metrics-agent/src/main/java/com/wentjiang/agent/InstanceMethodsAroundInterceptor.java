package com.wentjiang.agent;

import com.wentjiang.agent.common.MethodInfo;
import com.wentjiang.agent.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;

import static com.wentjiang.agent.util.LogUtil.*;

public abstract class InstanceMethodsAroundInterceptor {

    private static Logger logger = LoggerFactory.getLogger(InstanceMethodsAroundInterceptor.class);

    public void beforeMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, String requestId)
            throws Throwable {
        String metricJson = LogUtil.recordMetrics(getEnhanceClassName(), method, OPERATE_BEGIN, requestId,
                new HashMap<>());
        logger.info("metricJson: " + metricJson);
    }

    public Object afterMethod(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret,
            String requestId) throws Throwable {
        String metricJson = LogUtil.recordMetrics(getEnhanceClassName(), method, OPERATE_END, requestId,
                new HashMap<>());
        logger.info("metricJson: " + metricJson);
        return ret;
    }

    public void handleMethodException(Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t,
            String requestId) {
        String metricJson = LogUtil.recordMetrics(getEnhanceClassName(), method, OPERATE_EXCEPTION, requestId,
                new HashMap<>());
        logger.error("exception metricJson: " + metricJson);
    }

    public abstract String getEnhanceClassName();

    public abstract MethodInfo getMethodInfo();

}
