package com.april.fourth.aop;

import com.april.fourth.annotation.MockClient;
import com.april.fourth.bean.MockConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by daipengfei
 * on 2017/3/23.
 */

@Aspect
@Component
public class MockAop {
    @Resource
    private MockConfig mockConfig;

    @Around("execution(* *(..)) && @annotation(com.april.fourth.annotation.MockClient)")
    public Object mockAop(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature sig = (MethodSignature)joinPoint.getSignature();
        Class<?> clazz = joinPoint.getTarget().getClass();
        Method currentMethod = clazz.getMethod(sig.getName(), sig.getParameterTypes());
        MockClient annotation = AnnotationUtils.findAnnotation(currentMethod, MockClient.class);
        if(annotation == null ||StringUtils.isEmpty(annotation.key())){
            return mockConfig.getMap().get(clazz.getName() + "." + sig.getName());
        }
        return mockConfig.getMap().get(annotation.key());
    }

}
