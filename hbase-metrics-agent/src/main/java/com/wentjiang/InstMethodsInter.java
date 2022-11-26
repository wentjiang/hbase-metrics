package com.wentjiang;

import net.bytebuddy.implementation.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InstMethodsInter {
    private static final Logger logger = LoggerFactory.getLogger(InstMethodsInter.class);

    private InstanceMethodsAroundInterceptor interceptor;

    /**
     * @param instanceMethodsAroundInterceptorClassName
     *            class full name.
     */
    public InstMethodsInter(String instanceMethodsAroundInterceptorClassName, ClassLoader classLoader) {
        try {
            interceptor = InterceptorInstanceLoader.load(instanceMethodsAroundInterceptorClassName, classLoader);
        } catch (Throwable t) {
            throw new RuntimeException("Can't create InstanceMethodsAroundInterceptor.", t);
        }
    }

    /**
     * Intercept the target instance method.
     *
     * @param obj
     *            target class instance.
     * @param allArguments
     *            all method arguments
     * @param method
     *            method description.
     * @param zuper
     *            the origin call ref.
     * 
     * @return the return value of target instance method.
     * 
     * @throws Exception
     *             only throw exception because of zuper.call() or unexpected exception in sky-walking ( This is a bug,
     *             if anything triggers this condition ).
     */
    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> zuper,
            @Origin Method method) throws Throwable {
        String requestId = StringUtil.generateRequestId();
        System.out.println("run in intercept method: " + interceptor.getClass().getSimpleName());
        try {
            interceptor.beforeMethod(method, allArguments, method.getParameterTypes(), requestId);
        } catch (Throwable t) {
            logger.error("class[" + obj.getClass() + "] before method[" + method.getName() + "] intercept failure", t);
        }

        Object ret = null;
        try {
            ret = zuper.call();
        } catch (Throwable t) {
            try {
                interceptor.handleMethodException(method, allArguments, method.getParameterTypes(), t, requestId);
            } catch (Throwable t2) {
                logger.error("class[" + obj.getClass() + "] handle method[" + method.getName() + "] intercept failure",
                        t);
            }
            throw t;
        } finally {
            try {
                ret = interceptor.afterMethod(method, allArguments, method.getParameterTypes(), ret, requestId);
            } catch (Throwable t) {
                logger.error("class[" + obj.getClass() + "] after method[" + method.getName() + "] intercept failure",
                        t);
            }
        }
        return ret;
    }
}
