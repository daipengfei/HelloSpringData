package com.april.fourth.bean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
