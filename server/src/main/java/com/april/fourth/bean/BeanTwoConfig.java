package com.april.fourth.bean;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by daipengfei
 * on 2017/3/22.
 */
@Configuration
@ConditionalOnMissingBean(BeanTwo.class)
public class BeanTwoConfig {

    @Bean
    public BeanTwo beanTwo(BeanOne beanOne) {
        System.out.println("init beanTwo again !!!");
        return new BeanTwo(beanOne);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String json = "com.april.fourth.service.HelloPersonImpl.helloPerson-{\"addresses\":[\"a\",\"b\"],\"id\":1,\"name\":\"daipengfei\"}";
        String[] split = json.split("-");
        String fullMethodName = split[0];
        int lastIndexOfDot = fullMethodName.lastIndexOf(".");
        String className = fullMethodName.substring(0,lastIndexOfDot);
        String methodName = fullMethodName.substring(lastIndexOfDot + 1,fullMethodName.length());
        Method method = ReflectionUtils.findMethod(Class.forName(className), methodName);
        Object o = JSON.parseObject(split[1], method.getReturnType());
        System.out.println(o);
    }

}
