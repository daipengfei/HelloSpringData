package com.april.fourth.rule;

import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by daipengfei
 * on 2017/3/30.
 */

@Configuration
public class EasyRuleConfig implements ApplicationContextAware{
    private ApplicationContext applicationContext;

    @Bean
    public RulesEngine rulesEngine(){
        System.out.println("init engine!!!");
        Map<String, Rule> rules = applicationContext.getBeansOfType(Rule.class);
        Map<String, Object> rulesOfAnnotation = applicationContext.
                getBeansWithAnnotation(org.easyrules.annotation.Rule.class);
        rulesOfAnnotation.putAll(rules);
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();
        for(Object rule : rulesOfAnnotation.values()){
            rulesEngine.registerRule(rule);
        }
        return rulesEngine;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
