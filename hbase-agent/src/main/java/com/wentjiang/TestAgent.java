package com.wentjiang;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class TestAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain in testAgent");
        ClassFileTransformer transformer = new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                String classPkgName = className.replace('/', '.');
                if (!classPkgName.contains("MockHBase")) {
                    return classfileBuffer;
                }

                ClassPool pool = ClassPool.getDefault();
                try {
                    CtClass ctClass = pool.get(classPkgName);
                    CtMethod[] methods = ctClass.getDeclaredMethods();
                    for (CtMethod method : methods) {
                        method.insertBefore("System.out.println(\"方法" + method.getName() + "开始\");");
                        method.insertAfter("System.out.println(\"方法" + method.getName() + "结束\");");
                    }
                    return ctClass.toBytecode();
                } catch (NotFoundException | CannotCompileException | IOException e) {
                    e.printStackTrace();
                }
                return classfileBuffer;
            }
        };

        inst.addTransformer(transformer);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain");
    }

}
