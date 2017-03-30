package com.april.fourth.rule;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.easyrules.quartz.RulesEngineScheduler;
import org.easyrules.quartz.RulesEngineSchedulerException;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Created by daipengfei
 * on 2017/3/30.
 */
@Rule(name = "mySecondRule")
@Component
public class MySecondRule {

    @Condition
    public boolean when(){
        return true;
    }

    @Action
    public void then(){
        System.out.println("good easy rule!");
    }

    public static void main(String[] args) throws RulesEngineSchedulerException {
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .withSilentMode(true)
                .build();
        MySecondRule myFirstRule = new MySecondRule();
        rulesEngine.registerRule(myFirstRule);
        RulesEngineScheduler scheduler = RulesEngineScheduler.getInstance();
        scheduler.scheduleAtWithInterval(rulesEngine,new Date(),1);
        scheduler.start();
    }
}
