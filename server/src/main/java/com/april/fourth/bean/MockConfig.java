package com.april.fourth.bean;

import com.alibaba.fastjson.JSON;
import com.april.fourth.annotation.MockClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daipengfei
 * on 2017/3/23.
 */

@Component
public class MockConfig implements InitializingBean {
    private static final String json ="com.april.fourth.service.HelloPersonImpl.helloPerson-{\"addresses\":[\"a\",\"b\"],\"id\":1,\"name\":\"daipengfei\"}";

    private final Map<String,Object> map = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] split = json.split("-");
        String fullMethodName = split[0];
        int lastIndexOfDot = fullMethodName.lastIndexOf(".");
        String className = fullMethodName.substring(0,lastIndexOfDot);
        String methodName = fullMethodName.substring(lastIndexOfDot + 1,fullMethodName.length());
        Method method = ReflectionUtils.findMethod(Class.forName(className), methodName);
        MockClient annotation = AnnotationUtils.findAnnotation(method, MockClient.class);
        if(annotation == null){
            return;
        }
        Object o = JSON.parseObject(split[1], method.getReturnType());
        if(StringUtils.isEmpty(annotation.key())){
            map.put(fullMethodName,o);
        }
        map.put(annotation.key(),o);
    }

    public Map<String, Object> getMap() {
        return map;
    }

}
